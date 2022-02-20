package com.example.data

data class DetailsDataClass(
    val recipients: List<RecipientData>) {

}
data class RecipientData(
    val address: String,
    val company: String,
    val name: String,
    val zip: String
)

