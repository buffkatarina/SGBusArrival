package com.buffkatarina.busarrival.api

import com.buffkatarina.busarrival.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusApiService {
    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    object BusApi {
        val busApi: BusApiInterface by lazy {
            retrofit.create(BusApiInterface::class.java)
        }
    }
}