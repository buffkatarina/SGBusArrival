package com.buffkatarina.busarrival.data.db

import android.util.Log
import androidx.lifecycle.asLiveData
import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

class BusArrivalRepository(private val busArrivalDao: BusArrivalDao) {
    /*Abstracts BusArrivalDatabase(), BusArrivalDao()*/

    fun getAllBusStops(): Flow<List<String>> {
        return busArrivalDao.getAllBusStops()
    }
    fun searchBusStops(searchQuery: String?): Flow<List<String>> {
        Log.i("ASD", busArrivalDao.searchBusStops(searchQuery).asLiveData().value.toString())
        return busArrivalDao.searchBusStops(searchQuery)
    }
     fun insertBusStops(busStops: BusStops.BusStopData) {
        busArrivalDao.insertBusStops(busStops)
    }

    fun getAllBusServices(): Flow<List<String>> {
        return busArrivalDao.getAllBusServices()
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



}