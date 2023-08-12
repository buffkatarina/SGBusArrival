package com.buffkatarina.busarrival.ui.fragments.home.map

import android.widget.LinearLayout
import android.widget.TextView
import com.buffkatarina.busarrival.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MarkerWindow(
    private val map: MapView,
    private val title: String,
    private val description: String,
    private val onClickAction: OnClick,
) : InfoWindow(R.layout.map_info_window, map) {


    override fun onOpen(item: Any?) {
        closeAllInfoWindowsOn(map)
        val busStopCode = mView.findViewById<TextView>(R.id.title)
        busStopCode.text = title
        val mDescription = mView.findViewById<TextView>(R.id.description)
        mDescription.text = description
        val infoWindow = mView.findViewById<LinearLayout>(R.id.info_window)
        infoWindow.setOnClickListener {
            onClickAction.onClickAction(busStopCode.text as String)
        }
    }

    override fun onClose() {
    }

    fun interface OnClick {
        fun onClickAction(busStopCode: String?)

    }
}