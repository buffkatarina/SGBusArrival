package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.google.android.material.card.MaterialCardView


class SwipeTouchHelper(context: Context): ItemTouchHelper.Callback() {
    private var limitScrollX: Int = 0
    private var currentScrollX = 0
    private var initialScrollX = 0
    private var active = false
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {

        return makeMovementFlags(0,  ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {

        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)


        if (viewHolder.itemView.scrollX > limitScrollX) {
            viewHolder.itemView.scrollTo(limitScrollX, 0)
        }

        else if (viewHolder.itemView.scrollX < 0) {
            viewHolder.itemView.scrollTo(0, 0)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {

        limitScrollX = viewHolder.itemView.findViewById<MaterialCardView>(R.id.swipe_action).width
        val limitDx = dX.coerceIn(-limitScrollX.toFloat(), limitScrollX.toFloat())

        if (actionState == ACTION_STATE_SWIPE) {
            if (dX == 0f) {
                currentScrollX = viewHolder.itemView.scrollX
                active = true
            }
            if (isCurrentlyActive) {
                var scrollOffSet = currentScrollX - limitDx.toInt()
                if (scrollOffSet > limitScrollX) {
                    scrollOffSet = limitScrollX
                }

                else if (scrollOffSet < 0 ) {
                    scrollOffSet = 0
                }
                viewHolder.itemView.scrollTo(scrollOffSet, 0)
            }

            else {
                if (active) {
                    active = false
                    currentScrollX = viewHolder.itemView.scrollX
                    initialScrollX = dX.toInt()
                }
                if (viewHolder.itemView.scrollX < limitScrollX) {
                    viewHolder.itemView.scrollTo((currentScrollX*dX/initialScrollX).toInt(), 0)
                }
            }
        }

    }
}

