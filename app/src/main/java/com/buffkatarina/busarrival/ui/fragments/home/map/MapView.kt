package com.buffkatarina.busarrival.ui.fragments.home.map


import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

import org.osmdroid.config.Configuration.*
import org.osmdroid.util.GeoPoint


@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null
) {

    val mapViewState = mapLifeCycle()
    AndroidView(
        modifier = modifier,
        factory = {mapViewState},
    ) { mapView ->
        onLoad?.invoke(mapView) //?
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        val mapController = mapView.controller
        mapController.setZoom(9.5)
        val startPoint = GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint);


    }
}