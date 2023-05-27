package com.atejeda.starwars.data

import com.atejeda.starwars.data.network.HomeService
import javax.inject.Inject
import com.atejeda.starwars.core.Result
import com.atejeda.starwars.data.model.Element

class HomeRepository @Inject constructor(
    private val service:HomeService
) {

    suspend fun getAll():Result<List<Element>> = service.getAll()





}