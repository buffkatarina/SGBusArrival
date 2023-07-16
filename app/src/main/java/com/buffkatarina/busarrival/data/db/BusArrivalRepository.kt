package com.buffkatarina.busarrival.data.db

import com.buffkatarina.busarrival.data.entities.BusStops
class BusArrivalRepository(private val bussArrivalDao: BusArrivalDao) {
    /*Abstracts BusArrivalDatabase(), BusArrivalDao()*/

     fun insertBusStops(busStops: BusStops.BusStopData) {
        bussArrivalDao.insertBusStops(busStops)
    }
}