package com.atejeda.starwars.data.network

import com.atejeda.starwars.data.model.Element
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("all.json")
    suspend fun getAll(): Response<List<Element>>

}