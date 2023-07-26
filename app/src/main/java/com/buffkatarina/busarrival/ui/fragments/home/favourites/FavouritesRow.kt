package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription


@Composable
fun FavouritesRow(
    serviceNo: String,
    busStopCode: Int,
    description: String,
    timings: Triple<String, String, String>) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            ConstraintLayout {
                val (info, nextbus, nextbus2, nextbus3) = createRefs()
                Column{
                    Text(text = serviceNo, fontSize = 30.sp)
                    Text(text = description, fontSize = 30.sp)
                    Text(text = busStopCode.toString(), fontSize = 30.sp)
                }

                Text(
                    text = timings.first,
                    Modifier.constrainAs(nextbus) {
                        start.linkTo(info.end, margin= 10.dp)
                    }
                )
                Text(
                    text = timings.second,
                    Modifier.constrainAs(nextbus2) {
                        start.linkTo(nextbus.end, margin= 10.dp)
                    })
                Text(
                    text = timings.third,
                    Modifier.constrainAs(nextbus3) {
                        start.linkTo(nextbus2.end, margin= 10.dp)
                    })
            }


        }
    }
}

