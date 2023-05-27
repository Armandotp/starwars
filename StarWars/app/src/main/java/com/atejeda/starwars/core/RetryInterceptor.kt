package com.atejeda.starwars.core

import android.util.Log
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.net.SocketException

class RetryInterceptor(i: Int) : Interceptor{

    private var reintentos = i

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        var response: Response? = null
        var responseOK = false
        var tryCount = 0
        while (!responseOK && tryCount < reintentos) {
            try {
                Log.d("RetryInterceptor", "Intentando Request - $tryCount")
                response = chain.proceed(request)
                if (response.code() == 204) {
                    println("CODE -> " + 204)
                }
                responseOK =
                    response.isSuccessful or (response.code() in 400..499)
            } catch (e: Exception) {
                Log.d("RetryInterceptor", "Request sin exito - $tryCount")
                Log.d("RetryInterceptor", e.toString())
                Log.d("RetryInterceptor", e.stackTrace.toString())
            } finally {
                tryCount++
            }
        }
        if (response == null) {
            throw SocketException("Error no especificado de red")
        }
        return response
    }
}