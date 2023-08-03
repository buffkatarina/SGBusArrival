package com.buffkatarina.busarrival.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class BusServices(
    @SerializedName("odata.metadata")
    val metadata: String,

    @SerializedName("value")
    val data: List<BusServicesData>,
) {
    @Entity(tableName = "BusServices", primaryKeys = ["serviceNo", "direction"])
    data class BusServicesData(
        @SerializedName("ServiceNo")
        val serviceNo: String,

        @SerializedName("Operator")
        val operator: String,

        @SerializedName("Direction")
        val direction: Int,

        @SerializedName("Category")
        val category: String,

        @SerializedName("OriginCode")
        val originCode: String,

        @SerializedName("DestinationCode")
        val destinationCode: String,

        @SerializedName("AM_Peak_Freq")
        val amPeakFreq: String,

        @SerializedName("AM_Offpeak_Freq")
        val amOffPeakFreq: String,

        @SerializedName("PM_Peak_Freq")
        val pmPeakFreq: String,

        @SerializedName("PM_Offpeak_Freq")
        val pmOffPeakFreq: String,

        @SerializedName("LoopDesc")
        val loopDesc: String,
    )
}