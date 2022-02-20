package com.example.data


import android.content.Context
import android.util.Log
import com.example.data.Utils.isOnline
import com.example.data.Utils.request
import com.example.domain.*
import com.example.domain.repository.Repository


class RepositoryImpl(val context: Context) : Repository {

    override suspend fun loadOverviewData(): Either<Failure, List<Order>> {
        // load overview data only when network connection is established
        return when (isOnline(context)) {
            // request a response from API and map data onto domain data class or return failure
            true -> request(
                Utils.retrofitInstance(Utils.provideHttpClient()).getOrders()
            ) {
                it.orders.map {
                    Order(
                        it.creation_date ?: 0,
                        it.device ?: "",
                        it.id ?: 5,
                        it.image ?: "",
                        it.itemnumber ?: "",
                        it.recipient_count ?: 0,
                        it.status ?: "",
                        it.type ?: 0,
                        it.type_name ?: ""
                    )
                }
            }
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

    override suspend fun loadDetailsData(id: Int): Either<Failure, List<Recipient>> {
        // load details data only when network connection is established
        return when (isOnline(context)) {
            // request a response from API and map data onto domain data class
            true -> request(
                Utils.retrofitInstance(Utils.provideHttpClient()).getOrderDetails(id)
            ) {
                it.recipients.map {
                    Recipient(
                        it.address ?: "",
                        it.company ?: "",
                        it.name ?: "",
                        it.zip ?: ""
                    )
                }
            }
            false -> Either.Left(Failure.NetworkConnection)
        }
    }
}