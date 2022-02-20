package com.example.domain

import com.example.domain.repository.Repository


class LoadOverviewDomain (private val repository: Repository) {

    suspend fun execute() : Either<Failure, List<Order>> {
        return repository.loadOverviewData()
    }
}