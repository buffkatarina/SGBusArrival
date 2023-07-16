package com.buffkatarina.busarrival.data.entities

import com.google.gson.annotations.SerializedName


/*Data class to for storing
 deserialised bus routes JSON data from the api */
data class BusRoutes(
    @SerializedName("odata.metadata")
    val metadata: String,

    @SerializedName("value")
    val value: List<Data>
) {
    data class Data(
        @SerializedName("ServiceNo")
        val serviceNo: String,

        @SerializedName("Operator")
        val operator: String,

        @SerializedName("Direction")
        val direction: Int,

        @SerializedName("StopSequence")
        val stopSequence: String,

        @SerializedName("BusStopCode")
        val busStopCode: String,

        @SerializedName("Distance")
        val distance: String,

        @SerializedName("WD_FirstBus")
        val wdFirstBus: String,

        @SerializedName("WD_LastBus")
        val wdLastBus: String,

        @SerializedName("SAT_FirstBus")
        val satFirstBus: String,

        @SerializedName("SUN_FirstBus")
        val sunFirstBus: String,

        @SerializedName("SUN_LastBus")
        val sunLastBus: String
    )
}

