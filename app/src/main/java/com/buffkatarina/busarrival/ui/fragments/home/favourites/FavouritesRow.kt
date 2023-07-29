package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.arrivalTime
import com.buffkatarina.busarrival.data.entities.BusTimings


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritesRow(
    serviceNo: String,
    busStopCode: Int,
    description: String,
    timings: BusTimings.BusData) {

    val nextBus = arrivalTime(timings.nextBus.estimatedArrival)
    val nextBus2 = arrivalTime(timings.nextBus2.estimatedArrival)
    val nextBus3 = arrivalTime(timings.nextBus3.estimatedArrival)
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
    ) {
        Row(
            modifier = Modifier.padding(top = 3.dp, bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End)  {

            Column(
                modifier = Modifier.padding(end = 60.dp, start = 10.dp),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = serviceNo, fontSize = 20.sp)
                Text(
                    text = description,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .width(100.dp)
                        .basicMarquee(iterations = Int.MAX_VALUE, delayMillis = 0, initialDelayMillis = 0),
                    textAlign = TextAlign.Center)
            }

            Column(
                modifier = Modifier.padding(end = 20.dp),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.next),
                    fontSize = 20.sp)
                Text(
                    text = nextBus,
                    fontSize = 20.sp,
                    modifier = Modifier.padding( top = 3.dp))
            }

            Column(
                modifier = Modifier.padding(end = 20.dp, top = 0.dp),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.second),
                    fontSize = 20.sp)
                Text(
                    text = nextBus2,
                    fontSize = 20.sp,
                    modifier = Modifier.padding( top = 3.dp))
            }

            Column(
                modifier = Modifier.padding(end = 10.dp),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.third),
                    fontSize = 20.sp)
                Text(
                    text = nextBus3,
                    fontSize = 20.sp,
                    modifier = Modifier.padding( top = 3.dp))
            }

        }
    }
}

