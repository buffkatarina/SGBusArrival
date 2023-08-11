package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.arrivalTime
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.google.android.material.card.MaterialCardView
import kotlin.properties.Delegates


class BusTimingsAdapter(
    private val busStopCode: String,
    private val fragmentCallback: FragmentCallback,
) :
    RecyclerView.Adapter<BusTimingsAdapter.BusArrivalViewHolder>() {

    private var busTimings = emptyList<BusTimings.BusData>()
    private var favouriteBusServices = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusArrivalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_timings_recyclerview_row, parent, false)
        val viewHolder = BusArrivalViewHolder(view)
        setUpListener(viewHolder)
        return viewHolder
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpListener(holder: BusArrivalViewHolder) {
        val serviceNo = holder.serviceNoHolder
        val swipeFrame = holder.swipeFrame
        var buttonVisibility = false
        val itemView = holder.itemView
        val mainCard: MaterialCardView = holder.mainCard
        var cardMargin = 0
        swipeFrame.visibility = View.INVISIBLE
        val favouriteClickListener = OnClickListener {
            //Removes bus service from database when an activated button is tapped
            // Deactivates the button afterwards - shows up as uncolored star on display
            if (swipeFrame.cardBackgroundColor.defaultColor == Color.Red.toArgb()) {
                fragmentCallback.removeFavouriteBusService(busStopCode, serviceNo.text as String)
                holder.swipeFrame.setCardBackgroundColor(fragmentCallback.getColor(R.color.lime))
                holder.actionIcon.setBackgroundResource(R.drawable.star_on)

            }
            //Add bus service from database when an deactivated button is tapped
            // Activates the button afterwards - shows up as colored star on display
            else {
                fragmentCallback.addFavouriteBusService(busStopCode, serviceNo.text as String)
                holder.swipeFrame.setCardBackgroundColor(Color.Red.toArgb())
                holder.actionIcon.setBackgroundResource(R.drawable.delete_icon)
            }
            mainCard.animate().x(0f + cardMargin).setDuration(0).start()
            buttonVisibility = false
            swipeFrame.visibility = View.INVISIBLE

        }
        swipeFrame.setOnClickListener(favouriteClickListener)

        itemView.post {
            swipeFrame.updateLayoutParams {
                height = mainCard.height
            }
            val limit = swipeFrame.width.toFloat()
            cardMargin = mainCard.marginStart
            var dX by Delegates.notNull<Float>()
            var offSet = 0f
            val threshold = 0.5
            var shouldClick = false

            itemView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        shouldClick = true
                        dX = 0f - event.rawX
                    }

                    MotionEvent.ACTION_MOVE -> {
                        shouldClick = false
                        offSet = event.rawX + dX

                        if (!buttonVisibility) {//allow dragging left if button is not visible
                            if (offSet >= -limit && offSet <= 0f) {
                                swipeFrame.visibility = View.VISIBLE
                                mainCard.animate().x(offSet).setDuration(0).start()
                            }
                        }
                    }

                    MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                        if (shouldClick) fragmentCallback.toBusRoutes(holder.serviceNoHolder.text.toString())
                        else
                            if (offSet <= -limit * threshold) { // Complete swipe when drag is longer threshold of 0.5f
                                if (!buttonVisibility) {
                                    offSet = -limit
                                    mainCard.animate().x(offSet).setDuration(0).start()
                                    buttonVisibility = true
                                    swipeFrame.visibility = View.VISIBLE


                                }
                            }
                        if (offSet > -limit * threshold) { //undo swipe if below threshold
                            mainCard.animate().x(0f + cardMargin).setDuration(0).start()
                            buttonVisibility = false
                            swipeFrame.visibility = View.INVISIBLE

                        }
                    }

                }
                true
            }
        }
    }


    override fun onBindViewHolder(holder: BusArrivalViewHolder, position: Int) {
        if (busTimings.isNotEmpty()) {
            val currentItem = busTimings[position]

            holder.serviceNoHolder.text = currentItem.serviceNo

            if (currentItem.serviceNo in favouriteBusServices) {
                holder.swipeFrame.setCardBackgroundColor(Color.Red.toArgb())
                holder.actionIcon.setBackgroundResource(R.drawable.delete_icon)
            } else {
                holder.swipeFrame.setCardBackgroundColor(fragmentCallback.getColor(R.color.lime))
                holder.actionIcon.setBackgroundResource(R.drawable.star_on)
            }
            //Initialise tuple of estimated arrival time of bus 1/2/3: text view of bus 1/2/3
            val pairs = listOf(
                Pair(currentItem.nextBus.estimatedArrival, holder.nextBus),
                Pair(currentItem.nextBus2.estimatedArrival, holder.nextBus2),
                Pair(currentItem.nextBus3.estimatedArrival, holder.nextBus3)
            )
            for (pair in pairs) {
                pair.second.text = arrivalTime(pair.first)
            }

        }
    }


    override fun getItemCount() = busTimings.size

    fun updateTimings(timings: List<BusTimings.BusData>) {
        busTimings = timings
        notifyDataSetChanged()
    }

    fun updateFavourites(favouritesData: List<String>) {
        if (favouriteBusServices.isEmpty()) {
            favouriteBusServices = favouritesData
            notifyDataSetChanged()
        }
    }

    class BusArrivalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceNoHolder: TextView = view.findViewById(R.id.serviceNo)
        val nextBus: TextView = view.findViewById(R.id.nextBus)
        val nextBus2: TextView = view.findViewById(R.id.nextBus2)
        val nextBus3: TextView = view.findViewById(R.id.nextBus3)
        val swipeFrame: MaterialCardView = view.findViewById(R.id.swipe_action)
        val actionIcon: ImageView = view.findViewById(R.id.action_button_icon)
        val mainCard: MaterialCardView = view.findViewById(R.id.busTimings)

    }

    interface FragmentCallback {
        /*
        * Helper interface for adapter to be able to call fragment methods without
        * the need for passing context into the adapter
        * */

        fun addFavouriteBusService(busStopCode: String, serviceNo: String)

        fun removeFavouriteBusService(busStopCode: String, serviceNo: String)

        fun getColor(color: Int): Int //helper function to get color from resources

        fun toBusRoutes(query: String?) //will pass the service number to the bus routes fragment

    }
}



