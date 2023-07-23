package com.buffkatarina.busarrival.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteBusServices", primaryKeys = ["serviceNo", "busStopCode"])
data class FavouriteBusServices(
    @ColumnInfo("busStopCode")
    val busStopCode: Int,

    @ColumnInfo("serviceNo")
    val serviceNo: String,
    )