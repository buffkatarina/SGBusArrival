package com.buffkatarina.busarrival.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusStops
import com.google.android.material.card.MaterialCardView

class BusStopsSearchAdapter(private val busStopsHandler: ToBusTimings): RecyclerView.Adapter<BusStopsSearchAdapter.SearchAdapterViewHolder>() {
    private var dataList = emptyList<BusStops.BusStopData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : SearchAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.bus_stops_recyclerview_row, parent, false)
       return setUpViewHolder(view)
    }

    private fun setUpViewHolder(view: View): SearchAdapterViewHolder {
        val viewHolder = SearchAdapterViewHolder(view)
        viewHolder.card.setOnClickListener {
            busStopsHandler.toBusTimings(viewHolder.busStopCode.text as String)
        }
        return  viewHolder
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
        val card: MaterialCardView = view.findViewById(R.id.searchRow)
    }

     fun updateData(newData: List<BusStops.BusStopData>) {
        dataList = newData
        notifyDataSetChanged()
    }

    interface ToBusTimings {
        /*Handles value of tapped bus stop*/
        fun toBusTimings(query: String)
    }
}