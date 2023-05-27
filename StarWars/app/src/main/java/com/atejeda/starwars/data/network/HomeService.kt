package com.atejeda.starwars.data.network

import com.atejeda.starwars.core.Result;
import com.atejeda.starwars.data.model.Element
import com.atejeda.starwars.di.NetworkModule
import java.lang.Exception
import javax.inject.Inject

class HomeService @Inject constructor() {

    suspend fun getAll(): Result<List<Element>>{
        return try {
            var call = NetworkModule.provideApiService(
                NetworkModule.provideRetrofit()).getAll()
            if (call.isSuccessful) {
                return Result.success(call.body())
            } else {
                return Result.error("server${call.code()}")
            }
        }catch (e: Exception) {
            e.printStackTrace()
            return Result.error("Exception "+e.message!!)
        }
    }
}