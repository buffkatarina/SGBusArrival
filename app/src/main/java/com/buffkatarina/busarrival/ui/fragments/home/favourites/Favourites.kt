package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription
import com.buffkatarina.busarrival.timeDifference

@Composable
fun Favourites(
    favourites: List<FavouriteBusServicesWithDescription>,
    timings: MutableList<BusTimings>) {
    FavouritesList(favourites, timings)
}

@Composable
fun FavouritesList(
    favourites: List<FavouriteBusServicesWithDescription>,
    timings: MutableList<BusTimings>) {
    LazyColumn{
        items(favourites) {favourite ->
            val result = getTimings(favourite.serviceNo, favourite.busStopCode, timings)
            FavouritesRow(favourite.serviceNo,
                favourite.busStopCode,
                favourite.description,
                result as Triple<String, String, String>)
        }
    }
}

fun getTimings(
    serviceNo: String,
    busStopCode: Int,
    timings: MutableList<BusTimings> ): Triple<String, String, String>? {

    //Improve algorithm??
    for (obj in timings) {
        if (obj.busStopCode == busStopCode) {
            for (each in obj.services) {
                if (serviceNo == each.serviceNo) {
                    val nextBus = timeDifference(each.nextBus.estimatedArrival)
                    val nextBus2 = timeDifference(each.nextBus2.estimatedArrival)
                    val nextBus3 = timeDifference(each.nextBus3.estimatedArrival)
                    return Triple(nextBus, nextBus2, nextBus3)
                }
            }
        }
    }
    return null
}