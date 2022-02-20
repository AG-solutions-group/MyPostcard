package com.example.domain

import com.example.domain.repository.Repository


class LoadDetailsDomain (private val repository: Repository){

    suspend fun execute(id: Int) : Either<Failure, List<Recipient>> {
        return repository.loadDetailsData(id = id)
    }
}