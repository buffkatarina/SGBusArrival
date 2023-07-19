package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusTimings
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class BusTimingsAdapter(private val data: BusTimings) :
    RecyclerView.Adapter<BusTimingsAdapter.BusArrivalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusArrivalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_timings_recyclerview_row, parent, false)
        return BusArrivalViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusArrivalViewHolder, position: Int) {
        val currentItem = data.services[position]
        holder.serviceNoHolder.text = currentItem.serviceNo
        val listArrival = listOf<String>(currentItem.nextBus.estimatedArrival,
            currentItem.nextBus2.estimatedArrival,
            currentItem.nextBus3.estimatedArrival)
        val listHolder = mutableListOf<TextView>(holder.nextBus, holder.nextBus2,  holder.nextBus3)
        var foo = 0
        while (foo < 3){
            if (listArrival[foo].isEmpty()){
                listHolder[foo].text = "-"
            }
            else{
                val difference = timeDifference(listArrival[foo])
                if (difference.toInt() < 1) {
                    listHolder[foo].text = "Arr"
                }
                else{
                    listHolder[foo].text = timeDifference(listArrival[foo]) + " min"
                }

            }
            foo++
        }
    }

    override fun getItemCount() = data.services.size

    private fun timeDifference(time: String): String{
        /*
        Estimates difference between current time and given time.
        Returns the difference in MINUTE
        */
        val currentTime = LocalDateTime.now()
        val parsedTime = ZonedDateTime.parse(time).toLocalDateTime()
        return ChronoUnit.MINUTES.between(currentTime, parsedTime).toString()

    }

    class BusArrivalViewHolder(view: View): RecyclerView.ViewHolder(view){
        val serviceNoHolder: TextView = view.findViewById<TextView>(R.id.serviceNo)
        val nextBus: TextView = view.findViewById<TextView>(R.id.nextBus)
        val nextBus2: TextView = view.findViewById<TextView>(R.id.nextBus2)
        val nextBus3: TextView = view.findViewById<TextView>(R.id.nextBus3)
    }


}


