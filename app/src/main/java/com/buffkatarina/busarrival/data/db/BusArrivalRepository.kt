package com.buffkatarina.busarrival.data.db

import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops
class BusArrivalRepository(private val busArrivalDao: BusArrivalDao) {
    /*Abstracts BusArrivalDatabase(), BusArrivalDao()*/

     fun insertBusStops(busStops: BusStops.BusStopData) {
        busArrivalDao.insertBusStops(busStops)
    }

    fun insertBusServices(busServices: BusServices.BusServicesData) {
        busArrivalDao.insertBusServices(busServices)
    }

    fun insertBusRoutes(busRoutes: BusRoutes.BusRoutesData) {
        busArrivalDao.insertBusRoutes(busRoutes)
    }

}