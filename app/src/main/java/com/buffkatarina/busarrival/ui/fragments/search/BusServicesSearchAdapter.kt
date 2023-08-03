package com.buffkatarina.busarrival.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R

class BusServicesSearchAdapter(private val busRoutesHandler: ToBusRoutes) :
    RecyclerView.Adapter<BusServicesSearchAdapter.SearchAdapterViewHolder>() {

    private var dataList = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : SearchAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.bus_services_recyclerview_row, parent, false)

        return setUpViewHolder(view)
    }

    private fun setUpViewHolder(view: View): SearchAdapterViewHolder {
        val viewHolder = SearchAdapterViewHolder(view)
        val textView = viewHolder.textView
        val cardView = viewHolder.cardView
        cardView.setOnClickListener {
            busRoutesHandler.toBusRoutes(textView.text.toString())
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.textView.text = currentItem
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    fun updateData(newData: List<String>) {
        dataList = newData
        notifyDataSetChanged()
    }

    class SearchAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.busServiceNo)
        val cardView: CardView = view.findViewById(R.id.bus_services_card)

    }

    interface ToBusRoutes {
        /*Handles the value of the tapped service number for the fragment to use*/
        fun toBusRoutes(query: String)
    }
}