package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.buffkatarina.busarrival.R


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
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
    ) {
        Row(
            modifier = Modifier.padding(top = 3.dp, bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End)  {

            Column(
                modifier = Modifier.padding(end = 60.dp, start = 20.dp),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = serviceNo, fontSize = 20.sp)
                Text(text = description, fontSize = 15.sp)
            }

            Column(
                modifier = Modifier.padding(end = 20.dp),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.next),
                    fontSize = 20.sp)
                Text(
                    text = timings.first,
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
                    text = timings.second,
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
                    text = timings.third,
                    fontSize = 20.sp,
                    modifier = Modifier.padding( top = 3.dp))
            }




//            ConstraintLayout {
//                val (info, nextbus, nextbus2, nextbus3) = createRefs()
//
//                Text(text = serviceNo,
//                    fontSize = 30.sp,
//                    modifier = Modifier.constrainAs(info) {
//                        start.linkTo(parent.start)
//                        top.linkTo(parent.top)
//
//                })

//
//                Text(
//                    text = timings.first,
//                    Modifier.constrainAs(nextbus) {
//                        start.linkTo(info.end, margin = 500.dp)
//                        end.linkTo(nextbus2.start, margin= 10.dp)
//                        bottom.linkTo(parent.bottom)
//
//                    }
//                )
//                Text(
//                    text = timings.second,
//                    Modifier.constrainAs(nextbus2) {
//                        end.linkTo(nextbus3.start, margin= 10.dp)
//                        bottom.linkTo(parent.bottom)
//                    })
//                Text(
//                    text = timings.third,
//                    Modifier.constrainAs(nextbus3) {
//                        end.linkTo(parent.end, margin = 10.dp)
//                        bottom.linkTo(parent.bottom)
//                    })
//            }
//
//
        }
    }
}

