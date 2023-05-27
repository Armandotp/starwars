package com.atejeda.starwars.domain

import com.atejeda.starwars.core.Result
import com.atejeda.starwars.data.HomeRepository
import com.atejeda.starwars.data.model.Element
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    suspend fun getAll(): Result<List<Element>> = repository.getAll()
}