package com.buffkatarina.busarrival.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.bus_routes.BusRoutesFragment

class SearchFragment: Fragment(), BusServicesSearchAdapter.ToBusRoutes {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model.clearSearchQuery(true)
        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
        val busStopsRecyclerView: RecyclerView = view.findViewById(R.id.busStops_recycler_view)
        busStopsRecyclerView.layoutManager = LinearLayoutManager(context)
        val busStopsSearchAdapter = BusStopsSearchAdapter()
        busStopsRecyclerView.adapter =  busStopsSearchAdapter

        val busServicesRecyclerView: RecyclerView = view.findViewById(R.id.busServices_recycler_view)
        busServicesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val busServicesAdapter = BusServicesSearchAdapter(this)
        busServicesRecyclerView.adapter = busServicesAdapter

//        Load data
        model.searchQuery.observe(viewLifecycleOwner) { query ->
            model.searchBusStops(query).observe(viewLifecycleOwner) {data ->
                busStopsSearchAdapter.updateData(data)
            }
            model.searchBusServices(query).observe(viewLifecycleOwner) {data ->
                busServicesAdapter.updateData(data)
            }
        }
    }

    override fun addBusRoutesFragment(query: String) {
        parentFragmentManager.setFragmentResult("query", bundleOf("query" to query))
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, BusRoutesFragment(),"BusRoutesFragment")
            .hide(this)
            .addToBackStack(null)
            .commit()
    }
}