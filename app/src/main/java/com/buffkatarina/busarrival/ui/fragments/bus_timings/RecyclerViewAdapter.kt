package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.BusTimings

class BusArrivalAdapter(private val data: BusTimings) :
    RecyclerView.Adapter<BusArrivalAdapter.BusArrivalViewHolder>(){

    class BusArrivalViewHolder(view: View): RecyclerView.ViewHolder(view){
        val serviceNoHolder: TextView = view.findViewById<TextView>(R.id.serviceNo)
        val nextBus: TextView = view.findViewById<TextView>(R.id.nextBus)
        val nextBus2: TextView = view.findViewById<TextView>(R.id.nextBus2)
        val nextBus3: TextView = view.findViewById<TextView>(R.id.nextBus3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusArrivalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_layout, parent, false)
        return BusArrivalViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusArrivalViewHolder, position: Int) {
        val currentItem = data.services[position]
        holder.serviceNoHolder.text = currentItem.serviceNo
        holder.nextBus.text = currentItem.nextBus.estimatedArrival
        holder.nextBus2.text = currentItem.nextBus2.estimatedArrival
        holder.nextBus3.text = currentItem.nextBus3.estimatedArrival
    }

        override fun getItemCount() = data.services.size

}


