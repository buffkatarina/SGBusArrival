package com.buffkatarina.busarrival.api

import com.buffkatarina.busarrival.*
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BusApiInterface {
    /*Retrofit interface for object BusApi in BusApiService */

    @Headers("AccountKey:$API_KEY")
    @GET(BUS_TIMINGS)
    suspend fun getBusTimings(@Query("BusStopCode") busStopCode: Int?): BusTimings

    @Headers("AccountKey:$API_KEY")
    @GET(BUS_ROUTES)
    suspend fun getBusRoutes(@Query("\$skip") skipAmt: Int?): BusRoutes

    @Headers("AccountKey:$API_KEY")
    @GET(BUS_STOPS)
    suspend fun getBusStops(@Query("\$skip") skipAmt: Int?): BusStops

    @Headers("AccountKey:$API_KEY")
    @GET(BUS_SERVICES)
    suspend fun getBusServices(@Query("\$skip") skipAmt: Int?): BusServices
}

