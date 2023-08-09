package com.buffkatarina.busarrival.ui.fragments.home.map


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.VectorDrawable
import android.location.LocationManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusStops
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sin

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null,
    busStops: List<BusStops.BusStopData>?,

    ) {
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val mapViewState = mapLifeCycle()
    val context = LocalContext.current
    var mapEnabled by remember { mutableStateOf(false) }
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    mapEnabled = locationPermissions.allPermissionsGranted && locationManager.isLocationEnabled

    //register receiver to check if gps is turned on or off continuously
    val gpsReceiver = object : GpsReceiver() {
        override fun onGpsChanged(enabled: Boolean) {
            mapEnabled = enabled && locationPermissions.allPermissionsGranted
        }
    }
    context.registerReceiver(gpsReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))

    if (!mapEnabled) {
        Column(
            modifier = modifier.background(colorResource(id = R.color.lime)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.map_disabled))
            Button(onClick = {
                locationPermissions.launchMultiplePermissionRequest()
                context.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }) {
                Text(text = stringResource(id = R.string.enable_location))
            }
        }
    } else {
        //Update location
        var location by remember { mutableStateOf<GeoPoint?>(null) }
        AndroidView(
            modifier = modifier,
            factory = { mapViewState },
        ) { mapView ->
            onLoad?.invoke(mapView) //?
            mapView.setBuiltInZoomControls(false)
            mapView.setMultiTouchControls(true)
            mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
            val myLocationOverlay = myLocationOverlay(mapView, context)
            myLocationOverlay.runOnFirstFix {
                location = myLocationOverlay.myLocation
            }

            val mapController = mapView.controller
            location?.let { that ->
                mapController.setZoom(18.0) //zoom in once location is found
                busStops?.let {
                    for (busStop in it) {
                        //Only display bus stops < 1km away due to performance reasons
                        if (calculateDistance(
                                that.latitude,
                                that.longitude,
                                busStop.latitude.toDouble(),
                                busStop.longitude.toDouble()
                            ) < 1
                        ) {
                            setMarker(
                                mapView,
                                busStop.latitude,
                                busStop.longitude,
                                context,
                                busStop.busStopCode,
                                busStop.description
                            )
                        }
                    }

                }
            }
            mapView.invalidate()
        }
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
    val distance = 2 * 6371 * asin(
        sqrt(
            sin((rLat2 - rLat1) / 2).pow(2) + cos(rLat1) * cos(rLat2) * sin((rLong2 - rLong1) / 2).pow(
                2
            )
        )
    )
    return distance
}

fun vectorToBitMap(context: Context, drawable: Int): Bitmap {
    /*
    * Converts drawable to bit map
    * */
    return (ResourcesCompat.getDrawable(
        context.resources,
        drawable,
        null
    ) as VectorDrawable).toBitmap()
}

fun myLocationOverlay(mapView: MapView, context: Context): MyLocationNewOverlay {
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
    return mLocationOverlay
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