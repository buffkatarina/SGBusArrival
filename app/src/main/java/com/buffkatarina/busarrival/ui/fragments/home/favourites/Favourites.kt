package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.buffkatarina.busarrival.data.entities.BusTimings

@Composable
fun Favourites(data: BusTimings) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text("Favourite", style = MaterialTheme.typography.headlineMedium)
            }
        }
        items(data.services) { lol->
            Text(text = lol.serviceNo)
        }
    }
}

@Composable
fun favouritesList(data: BusTimings.BusData) {


}