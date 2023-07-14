package com.buffkatarina.busarrival.network

import com.buffkatarina.busarrival.API_KEY
import com.buffkatarina.busarrival.BUS_TIMINGS
import com.buffkatarina.busarrival.BUS_ROUTES
import com.buffkatarina.busarrival.data.BusTimings
import com.buffkatarina.busarrival.data.BusRoutes
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BusApiInterface {
    /*Retrofit interface for object BusApi in BusApiService */

    @Headers("AccountKey:$API_KEY")

    @GET(BUS_TIMINGS)
    suspend fun getBusTimings(@Query("BusStopCode") busStopCode: String?): BusTimings

    @GET(BUS_ROUTES)
    suspend fun  getBusRoutes(): BusRoutes
}

