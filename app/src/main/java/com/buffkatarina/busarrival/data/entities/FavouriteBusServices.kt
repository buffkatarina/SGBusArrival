package com.buffkatarina.busarrival.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "FavouriteBusServices", primaryKeys = ["serviceNo", "busStopCode"])
data class FavouriteBusServices(
    @ColumnInfo("busStopCode")
    val busStopCode: Int,

    @ColumnInfo("serviceNo")
    val serviceNo: String,
    )