package com.buffkatarina.busarrival.ui.fragments.bus_routes

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusRoutesFiltered
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.google.android.material.button.MaterialButton

class BusRoutesFragment: Fragment() {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bus_routes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getBusRoutes(view)
        super.onViewCreated(view, savedInstanceState)
    }


    private fun getBusRoutes(view: View)  {
        /*Gets the bus routes from the view model */
        parentFragmentManager.setFragmentResultListener("query", viewLifecycleOwner) { _, bundle ->
            val query = bundle.getString("query")
            model.searchBusRoutes(query)
            model.busRoutesList.observe(viewLifecycleOwner) {busRoutesList ->
                setUpLayout(view, busRoutesList)
            }
        }
    }
     private fun setUpLayout(view: View, busRoutesList: List<List<BusRoutesFiltered>>) {
         val recyclerView = view.findViewById<RecyclerView>(R.id.bus_routes_recycler_view)
         val currentDirection = view.findViewById<TextView>(R.id.direction)
         val directionChange = view.findViewById<ImageView>(R.id.change_direction)
         val busRoutesAdapter = BusRoutesAdapter()
         recyclerView.layoutManager = LinearLayoutManager(context)
         recyclerView.adapter = busRoutesAdapter
         busRoutesAdapter.setData(busRoutesList[0]) // index 0 for bus routes at direction 1
         currentDirection.text = "Direction 1"  //Assume direction 1 on fragment open for now

         directionChange.setColorFilter(ContextCompat.getColor(requireContext(), R.color.lime))
         //when there is no direction 2 for this bus service
         if (busRoutesList[1].isEmpty()) {
             directionChange.alpha = 0.3F
             directionChange.isClickable = false
         }

         else {
             directionChange.setOnClickListener {  //Changes the displayed list of direction when button is pressed
                 if (currentDirection.text == "Direction 1") {
                     busRoutesAdapter.setData(busRoutesList[1])
                     currentDirection.text = "Direction 2"
                 }
                 else{
                     busRoutesAdapter.setData(busRoutesList[0])
                     currentDirection.text = "Direction 1"
                 }
             }
         }
         }

}