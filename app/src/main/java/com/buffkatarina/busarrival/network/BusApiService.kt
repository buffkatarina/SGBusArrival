package com.buffkatarina.busarrival.network

import com.buffkatarina.busarrival.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

object BusApi{
    val busApi: BusApiInterface by lazy {
        retrofit.create(BusApiInterface::class.java)
    }
}