package com.buffkatarina.busarrival.data

import com.google.gson.annotations.SerializedName


/*Data class to for storing
 deserialised bus routes json data from the api */

data class BusStops(
    @SerializedName("odata.metadata")
    val metadata: String,
    @SerializedName("value")
    val data: List<BusStopData>
) {
    data class BusStopData(
        @SerializedName("BusStopCode")
        val busStopCode: String,

        @SerializedName("RoadName")
        val roadName: String,

        @SerializedName("Description")
        val description: String,

        @SerializedName("Latitude")
        val latitude: Float,

        @SerializedName("Longitude")
        val longitude: Float
    )
}

