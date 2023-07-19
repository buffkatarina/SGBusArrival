package com.buffkatarina.busarrival.data.entities

import androidx.room.ColumnInfo

data class BusRoutesFiltered(
    @ColumnInfo("direction")
    val direction: String,

    @ColumnInfo("serviceNo")
    val serviceNo: String,

    @ColumnInfo("stopSequence")
    val stopSequence: Int,

    @ColumnInfo("busStopCode")
    val busStopCode: String,

    @ColumnInfo("description")
    val description: String
)