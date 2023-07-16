package com.buffkatarina.busarrival.api

import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusStops
import com.buffkatarina.busarrival.data.entities.BusTimings

class BusApiRepository(private val busApiInterface: BusApiInterface) {
    suspend fun getBusTimings(query: String?): BusTimings {
        return busApiInterface.getBusTimings(query)
    }

    suspend fun getBusStops(query: Int?): BusStops {
        return busApiInterface.getBusStops(query)
    }
}