package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription

@Composable
fun Favourites(
    pair: Pair<List<FavouriteBusServicesWithDescription>,
            MutableList<BusTimings>>) {
    FavouritesList(pair)
}

@Composable
fun FavouritesList(
    pair: Pair<List<FavouriteBusServicesWithDescription>,
            MutableList<BusTimings>>) {
    LazyColumn{
        items(pair.first.size) {pos ->
            FavouritesRow(
                pair.first[pos].serviceNo,
                pair.first[pos].busStopCode,
                pair.first[pos].description,
                pair.second[pos].services[0])

        }
    }
}


