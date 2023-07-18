package com.buffkatarina.busarrival.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusStops

class BusStopsSearchAdapter: RecyclerView.Adapter<BusStopsSearchAdapter.SearchAdapterViewHolder>() {
    private var dataList = emptyList<BusStops.BusStopData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : SearchAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.bus_stops_recyclerview_row, parent, false)
        return SearchAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.busStopCode.text = currentItem.busStopCode
        holder.description.text = currentItem.description
        holder.roadName.text = currentItem.roadName
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class SearchAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val busStopCode: TextView = view.findViewById(R.id.busStopCode)
        val description: TextView = view.findViewById(R.id.description)
        val roadName: TextView = view.findViewById(R.id.roadName)
    }

     fun updateData(newData: List<BusStops.BusStopData>) {
        dataList = newData
        notifyDataSetChanged()
    }
}