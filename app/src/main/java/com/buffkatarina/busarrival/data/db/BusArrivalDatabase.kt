package com.buffkatarina.busarrival.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buffkatarina.busarrival.data.entities.BusStops

@Database(entities = [BusStops.BusStopData::class], version = 1, exportSchema = false)
abstract class BusArrivalDatabase: RoomDatabase() {
    abstract fun BusArrivalDao(): BusArrivalDao

    companion object {
        @Volatile
        private var dbInstance: BusArrivalDatabase? = null
        fun getInstance(context: Context): BusArrivalDatabase {
            if (dbInstance == null) {
                synchronized(this) {
                    dbInstance = Room.databaseBuilder(
                        context,
                        BusArrivalDatabase::class.java,
                        "BusArrivalDB"
                    ).build()
                }
            }
            return dbInstance!!
        }
    }
}




    


