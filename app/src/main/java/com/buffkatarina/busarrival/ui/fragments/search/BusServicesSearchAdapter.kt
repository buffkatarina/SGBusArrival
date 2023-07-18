package com.buffkatarina.busarrival.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops

class BusServicesSearchAdapter: RecyclerView.Adapter<BusServicesSearchAdapter.SearchAdapterViewHolder>() {
    private var dataList = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : SearchAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.bus_services_recyclerview_row, parent, false)
        return SearchAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.busServiceNo.text = currentItem
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class SearchAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val busServiceNo: TextView = view.findViewById(R.id.busServiceNo)

    }

     fun updateData(newData: List<String>) {
        dataList = newData
        notifyDataSetChanged()
    }
}