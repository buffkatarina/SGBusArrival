package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R

class SwipeFavourite(
    private val adapter: BusTimingsAdapter,
    private val context: Context,
): ItemTouchHelper.Callback() {

    private val background = ColorDrawable(ContextCompat.getColor(context, R.color.lime))
    private val icon = ContextCompat.getDrawable(context, R.drawable.star_off)

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {

            return false
        }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.notifyItemChanged(viewHolder.adapterPosition)
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

        val itemView: View = viewHolder.itemView
        val iconMargin: Int = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop: Int = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight
        val iconLeft: Int = itemView.right - iconMargin - icon.intrinsicWidth
        val iconRight: Int = itemView.right - iconMargin

         if (dX < 0) { // Swiping to the left
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(-(iconLeft - iconMargin*2), itemView.top, itemView.right, itemView.bottom)
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)

        }

        background.draw(c)
        icon.draw(c)

        var limitDx = dX //Limits the swipe width
        if (dX < (-icon.intrinsicWidth - iconMargin*2 )) {
            limitDx = (-icon.intrinsicWidth - iconMargin*2).toFloat()
        }
        super.onChildDraw(c, recyclerView, viewHolder, limitDx, dY, actionState, isCurrentlyActive);
    }

}