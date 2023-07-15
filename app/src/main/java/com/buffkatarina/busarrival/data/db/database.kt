package com.buffkatarina.busarrival.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buffkatarina.busarrival.data.BusStops

@Database(entities = [BusStops.BusStopData::class], version = 1)
abstract class BusArrivalDatabase: RoomDatabase() {
    abstract fun busStopsDao(): BusStopsDao

}


object Database{
    fun buildDB(context: Context): RoomDatabase{
        val db: RoomDatabase by lazy {
            Room.databaseBuilder(
                context,
                BusArrivalDatabase::class.java,
            "BusArrival").build()
        }
        return db
    }
    

}
