package com.buffkatarina.busarrival.ui.fragments.home.map


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.VectorDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusStops
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null,
    busStops: List<BusStops.BusStopData>
) {

    val mapViewState = mapLifeCycle()
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = {mapViewState},
    ) { mapView ->
        onLoad?.invoke(mapView) //?
        mapView.setBuiltInZoomControls(false)
        mapView.setMultiTouchControls(true)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        myLocationOverlay(mapView, context)
        mapController(mapView)
        for (busStop in busStops) {
            setMarker(
                mapView,
                busStop.latitude,
                busStop.longitude,
                context,
                busStop.busStopCode,
                busStop.description)
        }
        mapView.invalidate()


    }
}
fun vectorToBitMap(context: Context, drawable: Int): Bitmap {
    /*
    * Converts drawable to bit map
    * */
    return (ResourcesCompat.getDrawable(context.resources, drawable, null) as VectorDrawable).toBitmap()
}
fun myLocationOverlay(mapView: MapView, context: Context) {
    /*
    * Centers map on current location
    * */
    val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
    mLocationOverlay.enableMyLocation()
    mLocationOverlay.isEnabled = true
    mLocationOverlay.enableFollowLocation()
    val icon = vectorToBitMap(context, R.drawable.pin_icon)
    mLocationOverlay.setPersonIcon(icon)
    mLocationOverlay.setDirectionIcon(icon)
    mapView.overlays.add(mLocationOverlay)
}

fun mapController(mapView: MapView) {
    val mapController = mapView.controller
    mapController.setZoom(18.0)
}

fun setMarker(
    mapView: MapView,
    latitude: Float,
    longitude: Float,
    context: Context,
    title: String,
    description: String
) {
    val marker = Marker(mapView)
    val geoPoint = GeoPoint(latitude.toDouble(), longitude.toDouble())
    marker.position = geoPoint
    marker.icon = ContextCompat.getDrawable(context, R.drawable.bus_icon)
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
    marker.infoWindow = MarkerWindow(mapView, context, title, description)
    mapView.overlays.add(marker)


}