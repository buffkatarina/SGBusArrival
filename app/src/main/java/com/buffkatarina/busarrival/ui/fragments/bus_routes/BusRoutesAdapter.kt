package com.buffkatarina.busarrival.ui.fragments.bus_routes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusRoutesFiltered

class BusRoutesAdapter
    : RecyclerView.Adapter<BusRoutesAdapter.BusRoutesViewHolder>() {

    private var data = emptyList<BusRoutesFiltered>()

    class BusRoutesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val busStopCode: TextView = view.findViewById(R.id.busStopCode)
        val description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRoutesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bus_routes_recyclerview_row, parent ,false)
        return BusRoutesViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusRoutesViewHolder, position: Int) {
        val currentItem = data[position]
        val busStopCode = currentItem.busStopCode
        val description =  currentItem.description
        holder.busStopCode.text = busStopCode
        holder.description.text = description
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<BusRoutesFiltered>) {
        data = newData
        notifyDataSetChanged()
    }
}