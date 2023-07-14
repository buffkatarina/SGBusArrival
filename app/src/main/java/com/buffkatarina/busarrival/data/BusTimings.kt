package com.buffkatarina.busarrival.data

import com.google.gson.annotations.SerializedName

data class BusTimings (
    @SerializedName("odata.metadata")
    val metadata: String,

    @SerializedName("BusStopCode")
    val busStopCode: String,

    @SerializedName("Services")
    val value: List<BusData>
    ){

    data class BusData(
        @SerializedName("ServiceNo")
        val serviceNo: String,

        @SerializedName("Operator")
        val operator: String,

        @SerializedName("NextBus")
        val nextBus: NextBusData,

        @SerializedName("NextBus2")
        val nextBus2: NextBusData,

        @SerializedName("NextBus3")
        val nextBus3: NextBusData
    )

    data class NextBusData(
        @SerializedName("OriginCode")
        val originCode: String,

        @SerializedName("DestinationCode")
        val destinationCode: String,

        @SerializedName("EstimatedArrival")
        val estimatedArrival: String,

        @SerializedName("Latitude")
        val latitude: String,

        @SerializedName("Longitude")
        val longitude: String,

        @SerializedName("VisitNumber")
        val visitNumber: String,

        @SerializedName("Load")
        val load: String,

        @SerializedName("Feature")
        val feature: String,

        @SerializedName("Type")
        val type: String,
    )


}

