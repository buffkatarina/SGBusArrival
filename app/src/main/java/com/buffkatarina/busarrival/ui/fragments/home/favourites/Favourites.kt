package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
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
        items(pair.first.size) { pos ->
            FavouritesRow(
                pair.first[pos].serviceNo,
                pair.first[pos].busStopCode,
                pair.first[pos].description,
                pair.second[pos].services[0])
        }
    }
}


