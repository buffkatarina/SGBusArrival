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

    ///Updates bus services into database
    // Takes in bus services stored in a busServices: BusServices.BusServicesData object as arguments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusServices(vararg busServices: BusServices.BusServicesData)

    //All bus services have direction = 1 and are unique
    @Query("SELECT serviceNo FROM BusServices WHERE direction = 1 ")
    fun getAllBusServices(): Flow<List<String>>

    @Query("SELECT serviceNo FROM BusServices WHERE direction = 1 AND serviceNo LIKE :searchQuery ")
    fun searchBusServices(searchQuery: String?): Flow<List<String>>

    //Updates bus routes into database
    // Takes in bus routes stored in a BusRoutes.BusRoutesData object as arguments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusRoutes(vararg busRoutes: BusRoutes.BusRoutesData)

    //Gets the list of bus routes with the description of the bus stop
    @Query("SELECT serviceNo, stopSequence, direction, BusStops.busStopCode, BusStops.description FROM BusRoutes " +
            "INNER JOIN BusStops " +
            "ON BusStops.busStopCode = BusRoutes.busStopCode " +
            "WHERE serviceNo = :searchQuery " +
            "AND direction = :direction " +
            "ORDER BY direction ASC, stopSequence ASC ")
    fun searchBusRoutes(searchQuery: String?, direction: String): List<BusRoutesFiltered>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteBusService(favouriteBusServices: FavouriteBusServices)

    //Gets all favourite bus services together with the description of each bus stop
    @Query("SELECT BusStops.busStopCode, serviceNo, description FROM FavouriteBusServices " +
            "INNER JOIN BusStops " +
            "ON BusStops.busStopCode = FavouriteBusServices.busStopCode " +
            "ORDER BY BusStops.busStopCode ASC")
    fun getAllFavouriteBusServices(): Flow<List<FavouriteBusServicesWithDescription>>

    //Removes a specified favourite bus service record;
    // takes in the bus stop code and bus service no as arguments
    @Query("DELETE FROM FavouriteBusServices " +
            "WHERE busStopCode = :busStopCode AND serviceNo = :serviceNo ")
    fun removeFavouriteBusService(busStopCode: Int, serviceNo: String)

    //Filter for favourite bus services by bus stop code
    @Query("SELECT serviceNo FROM FavouriteBusServices WHERE busStopCode = :busStopCode")
    fun getFavouriteBusService(busStopCode: Int): List<String>

    //Get all bus stop codes without any duplicate
    @Query("SELECT busStopCode FROM FavouriteBusServices " +
            "GROUP BY busStopCode " +
            "ORDER BY busStopCode ASC")
    fun getFavouriteBusStops(): List<Int>
}

