package com.buffkatarina.busarrival.api

import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops
import com.buffkatarina.busarrival.data.entities.BusTimings

class BusApiRepository(private val busApiInterface: BusApiInterface) {
    suspend fun getBusTimings(query: String?): BusTimings {
        return busApiInterface.getBusTimings(query)
    }

    suspend fun getBusStops(query: Int?): BusStops {
        return busApiInterface.getBusStops(query)
    }

    suspend fun getBusServices(query: Int?): BusServices {
        return busApiInterface.getBusServices(query)
    }

    suspend fun getBusRoutes(query: Int?): BusRoutes {
        return busApiInterface.getBusRoutes(query)
    }

    suspend fun getBusTimingsByServiceNo(busStop: String?, serviceNo: String?): BusTimings {
        return busApiInterface.getBusTimingsByServiceNo(busStop, serviceNo)
    }

}