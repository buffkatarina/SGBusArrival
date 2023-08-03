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

fun arrivalTime(time: String): String {
    /*
    Estimates difference between current time and bus timing.
    Returns: '-' if argument passed is empty
             'Arr' if time difference is < 1
              Difference in timing otherwise in MINUTES
    */
    if (time.isEmpty()) {
        return "-"
    }
    val currentTime = LocalDateTime.now()
    val parsedTime = ZonedDateTime.parse(time).toLocalDateTime()
    val difference = ChronoUnit.MINUTES.between(currentTime, parsedTime).toString()

    if (difference.toInt() < 1) {
        return "Arr"
    }
    return difference

}

