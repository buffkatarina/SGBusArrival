package com.buffkatarina.busarrival.data.db

import androidx.room.Dao
import androidx.room.Query
import com.buffkatarina.busarrival.data.BusStops

@Dao
interface BusStopsDao{
    @Query("SELECT * FROM BusStops")
    fun getAll(): List<BusStops.BusStopData>
}