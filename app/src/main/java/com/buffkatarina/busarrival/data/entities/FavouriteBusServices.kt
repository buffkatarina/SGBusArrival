package com.buffkatarina.busarrival.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "FavouriteBusServices", primaryKeys = ["serviceNo", "busStopCode"])
data class FavouriteBusServices(
    @ColumnInfo("busStopCode")
    val busStopCode: String,

    @ColumnInfo("serviceNo")
    val serviceNo: String,
)

data class FavouriteBusServicesWithDescription(
    @ColumnInfo("busStopCode")
    val busStopCode: String,

    @ColumnInfo("serviceNo")
    val serviceNo: String,

    @ColumnInfo("description")
    val description: String,
)
