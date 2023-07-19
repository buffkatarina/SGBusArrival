package com.buffkatarina.busarrival.ui.fragments.bus_routes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusRoutesFiltered
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val direction1 = mutableListOf<BusRoutesFiltered>()
        val direction2 = mutableListOf<BusRoutesFiltered>()
        getBusRoutes(direction1, direction2)
        setUpRecyclerView(view, direction1, direction2)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBusRoutes(direction1: MutableList<BusRoutesFiltered>,
                             direction2: MutableList<BusRoutesFiltered>) {
        parentFragmentManager.setFragmentResultListener("query", viewLifecycleOwner) {
            _, bundle ->
            val query = bundle.getString("query")
            var i = 0
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
             val result = model.searchBusRoutes(query)
                while (i < result.size ) {
                    if (result[i].direction == "1") {
                        direction1.add(result[i])
                    }
                    else {
                        direction2.add(result[i])
                    }
                    i++
                }
            }
        }
    }

    private fun setUpRecyclerView(
        view: View,
        direction1: MutableList<BusRoutesFiltered>,
        direction2: MutableList<BusRoutesFiltered>) {
        /*CURRENTLY A MESSY BUNCH OF NONSENSE*/
        val recyclerView = view.findViewById<RecyclerView>(R.id.bus_routes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val busRoutesAdapter = BusRoutesAdapter()
        recyclerView.adapter = busRoutesAdapter
        busRoutesAdapter.setData(direction1)
        val currentDirection = view.findViewById<TextView>(R.id.direction)
        val directionChange = view.findViewById<MaterialButton>(R.id.direction_change)
        currentDirection.text = "Direction 1"

        directionChange.setOnClickListener {
            if (currentDirection.text == "Direction 1") {
                busRoutesAdapter.setData(direction2)
                currentDirection.text = "Direction 2"
            }
            else {
                busRoutesAdapter.setData(direction1)
                currentDirection.text = "Direction 1"
            }
        }

    }
}