package com.buffkatarina.busarrival.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops
import kotlinx.coroutines.flow.Flow

@Dao
interface BusArrivalDao{
    // BusStops table methods
    @Query("SELECT busStopCode FROM BusStops")
    fun getAllBusStops(): Flow<List<String>>

    @Query("SELECT * FROM BusStops" +
            " WHERE busStopCode LIKE :searchQuery ")
    fun searchBusStops(searchQuery: String?): Flow<List<BusStops.BusStopData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStops(vararg busStops: BusStops.BusStopData)

    // BusServices table methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusServices(vararg busServices: BusServices.BusServicesData)

    //  direction = 1 to get unique bus 'serviceNo' as 1
    //  serviceNo can have 2 directions
    //  all serviceNo have direction = 1 but not all have direction = 2
    @Query("SELECT serviceNo FROM BusServices WHERE direction = 1 ")
    fun getAllBusServices(): Flow<List<String>>

    @Query("SELECT serviceNo FROM BusServices WHERE direction = 1 AND serviceNo LIKE :searchQuery ")
    fun searchBusServices(searchQuery: String?): Flow<List<String>>

    // BusRoutes table methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusRoutes(vararg busRoutes: BusRoutes.BusRoutesData)

    @Query("SELECT * FROM BusRoutes WHERE serviceNo = :serviceNo ORDER BY stopSequence ASC")
    fun getBusRoutes(serviceNo: String?)
}

