package com.buffkatarina.busarrival.data.db

import android.util.Log
import androidx.lifecycle.asLiveData
import com.buffkatarina.busarrival.data.entities.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

class BusArrivalRepository(private val busArrivalDao: BusArrivalDao) {
    /*Abstracts BusArrivalDatabase(), BusArrivalDao()*/

    fun searchBusStops(searchQuery: String?): Flow<List<BusStops.BusStopData>> {
        return busArrivalDao.searchBusStops(searchQuery)
    }
     fun insertBusStops(busStops: BusStops.BusStopData) {
        busArrivalDao.insertBusStops(busStops)
    }


    fun searchBusServices(searchQuery: String?): Flow<List<String>> {
        return busArrivalDao.searchBusServices(searchQuery)
    }
    fun insertBusServices(busServices: BusServices.BusServicesData) {
        busArrivalDao.insertBusServices(busServices)
    }

    fun insertBusRoutes(busRoutes: BusRoutes.BusRoutesData) {
        busArrivalDao.insertBusRoutes(busRoutes)
    }

    fun searchBusRoutes(searchQuery: String?, direction: String): List<BusRoutesFiltered> {
        return busArrivalDao.searchBusRoutes(searchQuery, direction)
    }

    fun insertFavouriteBusService(favouriteBusServices: FavouriteBusServices) {
        return busArrivalDao.insertFavouriteBusService(favouriteBusServices)
    }
}