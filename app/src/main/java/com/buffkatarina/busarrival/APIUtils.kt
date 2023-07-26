package com.buffkatarina.busarrival

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

const val BASE_URL: String = "http://datamall2.mytransport.sg/ltaodataservice/"
const val API_KEY: String = "qQ1eTprPQyCFkXL+6Ur0lA=="
const val BUS_ROUTES: String = "BusRoutes"
const val BUS_TIMINGS: String = "BusArrivalv2"
const val BUS_STOPS: String = "BusStops"
const val BUS_SERVICES: String = "BusServices"

fun timeDifference(time: String): String{
    /*
    Estimates difference between current time and given time.
    Returns the difference in MINUTES
    */
    if (time.isEmpty()) {
        return "-"
    }
    val currentTime = LocalDateTime.now()
    val parsedTime = ZonedDateTime.parse(time).toLocalDateTime()
    return ChronoUnit.MINUTES.between(currentTime, parsedTime).toString()

}

