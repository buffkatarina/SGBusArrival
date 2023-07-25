package com.buffkatarina.busarrival.data.entities

import androidx.room.ColumnInfo


//Stores the bus routes after filtering and joining with busStops table
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