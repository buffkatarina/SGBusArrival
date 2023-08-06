package com.buffkatarina.busarrival.ui.fragments.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.buffkatarina.busarrival.data.entities.BusStops
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.home.favourites.Favourites
import com.buffkatarina.busarrival.ui.fragments.home.map.MapView

@Composable
fun HomeCompose(
    databaseBuildState: Boolean,
    dialogState:Boolean,
    favouriteTimings: MutableLiveData<MutableList<BusTimings>>,
    viewModel: ActivityViewModel,
    ){

//Only show dialog on app launch

    if (!dialogState) {
        Dialog(databaseBuildState, viewModel::setDialogState)
    }
    if (databaseBuildState) {
        viewModel.getAllBusStops()
        val busStops by viewModel.busStops.observeAsState()
        val favourites by viewModel.getAllFavouriteBusServices().observeAsState()
        val timings by favouriteTimings.observeAsState()

        Column {
            busStops?.let {
                MapView(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp )
                        .weight(1f),
                    busStops = it
                )
            }
            if (favourites != null && timings != null) {
                if (favourites!!.size == timings!!.size) {
                    Favourites(
                        modifier = Modifier.weight(1f),
                        (favourites to timings) as Pair<List<FavouriteBusServicesWithDescription>, MutableList<BusTimings>>,
                        viewModel::removeFavouriteBusService)
                }

            }
        }
    }
}