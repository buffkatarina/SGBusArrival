package com.buffkatarina.busarrival.ui.fragments.home.map

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager


abstract class GpsReceiver : BroadcastReceiver() {
    /*
    * Implements BroadcastReceiver
    * Gets called when location is turned on or off
    * */
    override fun onReceive(context: Context?, intent: Intent?) {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        onGpsChanged(locationManager.isLocationEnabled)

    }

    abstract fun onGpsChanged(enabled: Boolean)

}