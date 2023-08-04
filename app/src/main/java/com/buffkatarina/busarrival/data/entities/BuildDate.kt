package com.buffkatarina.busarrival.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/*
* Stores the last build date of the database
* i.e. the latest time bus routes, bus stops and bus services data were refreshed
* */
@Entity(tableName = "BuildDate")
data class BuildDate(
    @PrimaryKey
    val id: Int,

    val buildDate: String
)