package com.buffkatarina.busarrival.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buffkatarina.busarrival.data.entities.*
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

    //All bus services have direction = 1
    @Query("SELECT serviceNo FROM BusServices WHERE direction = 1 ")
    fun getAllBusServices(): Flow<List<String>>

    @Query("SELECT serviceNo FROM BusServices WHERE direction = 1 AND serviceNo LIKE :searchQuery ")
    fun searchBusServices(searchQuery: String?): Flow<List<String>>

    // BusRoutes table methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusRoutes(vararg busRoutes: BusRoutes.BusRoutesData)

    @Query("SELECT serviceNo, stopSequence, direction, BusStops.busStopCode, description FROM BusRoutes " +
            "INNER JOIN BusStops " +
            "ON BusStops.busStopCode = BusRoutes.busStopCode " +
            "WHERE serviceNo = :searchQuery " +
            "AND direction = :direction " +
            "ORDER BY direction ASC, stopSequence ASC ")
    fun searchBusRoutes(searchQuery: String?, direction: String): List<BusRoutesFiltered>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteBusService(favouriteBusServices: FavouriteBusServices)

    @Query("SELECT * FROM FavouriteBusServices")
    fun getAllFavouriteBusServices(): Flow<List<FavouriteBusServices>>
}

