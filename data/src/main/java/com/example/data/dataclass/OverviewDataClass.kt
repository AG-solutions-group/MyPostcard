package com.example.data

data class OverviewDataClass(
    val orders: List<OrderData>){

}
data class OrderData(
    val creation_date: Int,
    val device: String?, // fix null values with null possibility -> adapted would be a better solution to fix bad results?
    val id: Int,
    val image: String,
    val itemnumber: String,
    val recipient_count: Int,
    val status: String,
    val type: Int,
    val type_name: String
)

