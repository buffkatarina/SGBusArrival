package com.buffkatarina.busarrival.ui.fragments.home.map


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.VectorDrawable
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusStops
import org.osmdroid.api.IGeoPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sin


@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null,
    busStops: List<BusStops.BusStopData>?,
    location: Location?,
) {

    val mapViewState = mapLifeCycle()
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = { mapViewState },
    ) { mapView ->
        onLoad?.invoke(mapView) //?
        mapView.setBuiltInZoomControls(false)
        mapView.setMultiTouchControls(true)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        myLocationOverlay(mapView, context)

        val mapController = mapView.controller
        mapController.setZoom(12.0)
        mapController.setCenter(GeoPoint(1.2794,103.8499)) //always centre in singapore first

        location?.let { that ->
            busStops?.let { it ->
                for (busStop in it) {
                    //Only display bus stops < 1km away due to performance reasons
                    if (calculateDistance(
                            that.latitude,
                            that.longitude,
                            busStop.latitude.toDouble(),
                            busStop.longitude.toDouble()) < 1
                    ) {
                        setMarker(
                            mapView,
                            busStop.latitude,
                            busStop.longitude,
                            context,
                            busStop.busStopCode,
                            busStop.description)
                    }
                }
                mapController.setZoom(18.0) //zoom in once location is found

            }

        }
        mapView.invalidate()
    }
}

fun calculateDistance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
    /*
    * Calculates the distance between two sets of coordinates using the haversine formula
    * */

    val rLat1 = toRadians(lat1)
    val rLat2 = toRadians(lat2)
    val rLong1 = toRadians(long1)
    val rLong2 = toRadians(long2)
    val distance = 2 * 6371 * asin(sqrt(
        sin((rLat2 - rLat1) / 2).pow(2) + cos(rLat1) * cos(rLat2) * sin((rLong2 - rLong1) / 2).pow(2)
    )
    )
    return distance
}

fun vectorToBitMap(context: Context, drawable: Int): Bitmap {
    /*
    * Converts drawable to bit map
    * */
    return (ResourcesCompat.getDrawable(context.resources,
        drawable,
        null) as VectorDrawable).toBitmap()
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





fun setMarker(
    mapView: MapView,
    latitude: Float,
    longitude: Float,
    context: Context,
    title: String,
    description: String,
) {

    val marker = Marker(mapView)
    val geoPoint = GeoPoint(latitude.toDouble(), longitude.toDouble())
    marker.position = geoPoint
    marker.icon = ContextCompat.getDrawable(context, R.drawable.bus_icon)
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
    marker.infoWindow = MarkerWindow(mapView, context, title, description)
    mapView.overlays.add(marker)


}