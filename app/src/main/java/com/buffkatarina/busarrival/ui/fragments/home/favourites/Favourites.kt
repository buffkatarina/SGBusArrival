package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favourites(
    modifier: Modifier  = Modifier,
    pair: Pair<List<FavouriteBusServicesWithDescription>,
            MutableList<BusTimings>>,
    removeFromFavourites: (Int, String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(pair.first.size) { pos ->

            val serviceNo = pair.first[pos].serviceNo
            val busStopCode = pair.first[pos].busStopCode
            val description = pair.first[pos].description
            val timings = pair.second[pos].services[0]

            val dismissState = rememberDismissState(
                confirmValueChange = {
                    if (it == DismissValue.DismissedToStart) {
                        removeFromFavourites(busStopCode, serviceNo)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState, background = {
                    val color by animateColorAsState(
                        when (dismissState.dismissDirection) {
                            DismissDirection.EndToStart -> Color.Red
                            else -> {
                                Color.Transparent
                            }
                        }
                    )
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(color)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()) {
                            Image(
                                modifier = Modifier.padding(end = 10.dp),
                                painter = painterResource(id = R.drawable.delete_icon),
                                contentDescription = stringResource(id = R.string.delete),
                            )
                        }

                    }
                },
                dismissContent = {
                    FavouritesRow(
                        serviceNo,
                        description,
                        timings)
                },
                directions = setOf(DismissDirection.EndToStart))
        }
    }
}


