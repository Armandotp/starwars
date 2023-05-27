package com.atejeda.starwars.di


import com.atejeda.starwars.core.RetryInterceptor
import com.atejeda.starwars.data.network.APIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{

        var gson: Gson = GsonBuilder().setLenient().serializeNulls().create()
        val fileLogger =
            HttpLoggingInterceptor.Logger { s ->
                try {
                    val outputStream =
                        FileOutputStream(File("/sdcard/loghttp.txt"), true)
                    outputStream.write(s.toByteArray())
                    outputStream.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        val interceptor = HttpLoggingInterceptor()
        val fileLoggingInterceptor = HttpLoggingInterceptor(fileLogger)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val retryInterceptor = RetryInterceptor(5)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(fileLoggingInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(retryInterceptor)
            .connectTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(20000, TimeUnit.SECONDS)
            .writeTimeout(50000, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://akabab.github.io/starwars-api/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}