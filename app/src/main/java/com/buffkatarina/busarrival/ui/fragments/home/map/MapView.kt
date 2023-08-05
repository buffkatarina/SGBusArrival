package com.buffkatarina.busarrival.ui.fragments.home.map


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null,
) {

    val mapViewState = mapLifeCycle()
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = {mapViewState},
    ) { mapView ->
        onLoad?.invoke(mapView) //?
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        myLocationOverlay(mapView, context)
        mapController(mapView)


    }
}

fun myLocationOverlay(mapView: MapView, context: Context, ) {
    val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
    mLocationOverlay.enableMyLocation()
    mLocationOverlay.isEnabled = true
    mLocationOverlay.enableFollowLocation()
    mapView.overlays.add(mLocationOverlay)
}

fun mapController(mapView: MapView) {
    val mapController = mapView.controller
    mapController.setZoom(17.0)
}