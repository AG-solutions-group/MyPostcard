package com.example.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface OrdersApiSearch {

    // place the GET request to PHP file to get the data from server and store it in a data class
    @GET("/api/cc/list.php")
    suspend fun getOrders(): Response<OverviewDataClass>

    @GET("/api/cc/detail.php")
    suspend fun getOrderDetails(
        @Query("id") id : Int
    ): Response<DetailsDataClass>
    // ?id=14876098

}