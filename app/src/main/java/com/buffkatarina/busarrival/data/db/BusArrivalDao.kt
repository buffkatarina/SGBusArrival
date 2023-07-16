package com.buffkatarina.busarrival.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops

@Dao
interface BusArrivalDao{
    @Query("SELECT * FROM BusStops")
    fun getAll(): List<BusStops.BusStopData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStops(vararg busStops: BusStops.BusStopData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusServices(vararg busServices: BusServices.BusServicesData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusRoutes(vararg busRoutes: BusRoutes.BusRoutesData)
}

