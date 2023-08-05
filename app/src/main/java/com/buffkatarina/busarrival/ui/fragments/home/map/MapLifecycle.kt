package com.buffkatarina.busarrival.ui.fragments.home.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.buffkatarina.busarrival.R
import org.osmdroid.views.MapView

@Composable
fun mapLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            clipToOutline = true
            R.id.map
        }
    }

    val lifeCycleObserver = mapLifeCycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifeCycleObserver)
        onDispose {
            lifecycle.removeObserver(lifeCycleObserver)
        }
    }
    return mapView

}

@Composable
fun mapLifeCycleObserver(mapView: MapView): LifecycleEventObserver = remember(mapView) {
    LifecycleEventObserver {_, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            else -> {}
        }
    }
}