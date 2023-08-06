package com.buffkatarina.busarrival.ui.fragments.home.map

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.ui.fragments.bus_timings.BusTimingFragment
import com.buffkatarina.busarrival.ui.fragments.home.HomeFragment
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MarkerWindow(
    private val map: MapView,
    context: Context,
    private val title: String,
    private val description: String,
): InfoWindow(R.layout.map_info_window, map) {
    private val activity = context as AppCompatActivity

    override fun onOpen(item: Any?) {
        closeAllInfoWindowsOn(map)

        val busStopCode = mView.findViewById<TextView>(R.id.title)
        busStopCode.text = title
        val mDescription = mView.findViewById<TextView>(R.id.description)
        mDescription.text = description

        val infoWindow = mView.findViewById<LinearLayout>(R.id.info_window)
        val fragmentManager = activity.supportFragmentManager
        infoWindow.setOnClickListener {
            fragmentManager.setFragmentResult("busStopCodeKey", bundleOf("busStopCode" to title))
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolder, BusTimingFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onClose() {
    }
}