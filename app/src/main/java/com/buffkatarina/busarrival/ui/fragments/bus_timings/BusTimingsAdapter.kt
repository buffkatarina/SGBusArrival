package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.arrivalTime
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.google.android.material.card.MaterialCardView
import kotlin.properties.Delegates


class BusTimingsAdapter(
    private val busStopCode: Int,
    private val favouritesHandler: FavouritesHandler,
) :
    RecyclerView.Adapter<BusTimingsAdapter.BusArrivalViewHolder>() {

    var busTimings = emptyList<BusTimings.BusData>()
    private var favouriteBusServices = emptyList<String>(

    )

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
        var swipeFrameMarginStart = 0
        var cardWidth = 0f
        swipeFrame.setOnClickListener {
            //Removes bus service from database when an activated button is tapped
            // Deactivates the button afterwards - shows up as uncolored star on display
            if (swipeFrame.cardBackgroundColor.defaultColor == Color.Red.toArgb()) {
                favouritesHandler.removeFavouriteBusService(busStopCode, serviceNo.text as String)
                holder.swipeFrame.setCardBackgroundColor(favouritesHandler.getColor(R.color.lime))
                holder.actionText.setText(R.string.add)
                holder.actionIcon.setBackgroundResource(R.drawable.star_on)
            }
            //Add bus service from database when an deactivated button is tapped
            // Activates the button afterwards - shows up as colored star on display
            else {
                favouritesHandler.addFavouriteBusService(busStopCode, serviceNo.text as String)
                holder.swipeFrame.setCardBackgroundColor(Color.Red.toArgb())
                holder.actionText.setText(R.string.remove)
                holder.actionIcon.setBackgroundResource(R.drawable.ic_baseline_delete_24)
            }
            mainCard.animate().x(0f + cardMargin).setDuration(0).start()
            swipeFrame.animate().x(-cardWidth - swipeFrameMarginStart).setDuration(0).start()
            buttonVisibility = false
        }


        itemView.post {
            val limit = swipeFrame.width.toFloat()
            cardWidth = mainCard.width.toFloat()
            swipeFrameMarginStart = swipeFrame.marginStart
            val swipeFrameMarginEnd = swipeFrame.marginEnd
            cardMargin = mainCard.marginStart
            var dX by Delegates.notNull<Float>()
            var offSet = 0f
            val threshold = 0.5

            itemView.setOnTouchListener { _, event ->

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = 0f - event.rawX
                    }

                    MotionEvent.ACTION_MOVE -> {
                        offSet = event.rawX + dX

                        if (!buttonVisibility) {//allow dragging left if button is not visible
                            if (offSet >= -limit - swipeFrameMarginEnd && offSet <= 0f) {
                                mainCard.animate().x(offSet).setDuration(0).start()
                                swipeFrame.animate()
                                    .x(offSet + cardWidth + swipeFrameMarginStart)
                                    .setDuration(0)
                                    .start()
                            }
                        }
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        if (offSet <= -limit * threshold) { // Complete swipe when drag is longer threshold of 0.5f
                            if (!buttonVisibility) {
                                offSet = -limit - swipeFrameMarginEnd
                                mainCard.animate().x(offSet).setDuration(0).start()
                                swipeFrame.animate()
                                    .x(offSet + cardWidth + swipeFrameMarginStart)
                                    .setDuration(0)
                                    .start()
                                buttonVisibility = true

                            }
                        }
                        if (offSet > -limit * threshold) { //undo swipe if below threshold
                            mainCard.animate().x(0f + cardMargin).setDuration(0).start()
                            swipeFrame.animate().x(-cardWidth - swipeFrameMarginStart)
                                .setDuration(0).start()
                            buttonVisibility = false

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
                holder.actionText.setText(R.string.remove)
                holder.actionIcon.setBackgroundResource(R.drawable.ic_baseline_delete_24)
            } else {
                holder.swipeFrame.setCardBackgroundColor(favouritesHandler.getColor(R.color.lime))
                holder.actionText.setText(R.string.add)
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
        val actionText: TextView = view.findViewById(R.id.action_button_text)
        val mainCard: MaterialCardView = view.findViewById(R.id.busTimings)

    }

    interface FavouritesHandler {
        /*For recycler view to be able to call view model to remove or add favourite bus services*/
        fun addFavouriteBusService(busStopCode: Int, serviceNo: String)

        fun removeFavouriteBusService(busStopCode: Int, serviceNo: String)

        fun getColor(color: Int): Int //helper function to get color from resources
    }

}


