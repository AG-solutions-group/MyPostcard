package com.example.domain.repository

import com.example.domain.*


interface Repository {

    suspend fun loadOverviewData() : Either<Failure, List<Order>>

    suspend fun loadDetailsData(id: Int) : Either<Failure, List<Recipient>>

}