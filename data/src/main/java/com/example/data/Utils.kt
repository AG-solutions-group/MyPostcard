package com.example.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.domain.Either
import com.example.domain.Failure
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Utils {

    fun provideHttpClient(): OkHttpClient {

        // build okhttpclient
        val builder = OkHttpClient.Builder()
        //  builder.interceptors().add(AuthenticationInterceptor())

        return builder.build()
    }

    fun retrofitInstance(client: OkHttpClient): OrdersApiSearch {

        // use retrofit to get API data
        val api: OrdersApiSearch by lazy {
            Retrofit.Builder()
                .baseUrl("https://www.mypostcard.com/")
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        //        .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(OrdersApiSearch::class.java)
        }
        return api
    }


    // extension object that returns either data or failure
    fun <T, R> request(
        response: Response<T>,
        transform: (T) -> R,
    ): Either<Failure, R> {
        return try {
            when (response.isSuccessful && response.body() != null) {
                true -> Either.Right(transform((response.body()!!)))
                false -> Either.Left(Failure.ServerError)
            }

        } catch (exception: Throwable) {
            Log.d("main", exception.toString())
            Either.Left(Failure.ServerError)
        }
    }

    // network connection checker
    fun isOnline(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}

/*
class AuthenticationInterceptor : Interceptor {

    // add the header to the request

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}

*/