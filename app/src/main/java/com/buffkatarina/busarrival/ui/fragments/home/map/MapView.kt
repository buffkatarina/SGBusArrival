package com.buffkatarina.busarrival.ui.fragments.home.map


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null
) {

    val mapViewState = mapLifeCycle()


    AndroidView(
        factory = {mapViewState},
    ) { mapView ->
        onLoad?.invoke(mapView)
    }
}