package com.example.domain


data class Order(
    val creation_date: Int,
    val device: String,
    val id: Int,
    val image: String,
    val itemnumber: String,
    val recipient_count: Int,
    val status: String,
    val type: Int,
    val type_name: String
)

