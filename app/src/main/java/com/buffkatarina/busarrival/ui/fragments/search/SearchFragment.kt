package com.buffkatarina.busarrival.ui.fragments.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel

class SearchFragment: Fragment() {
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
        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
//      Set up recyclerViews
        val busStopsRecyclerView: RecyclerView = view.findViewById(R.id.busStops_recycler_view)
        busStopsRecyclerView.layoutManager = LinearLayoutManager(context)
        val busStopsSearchAdapter = BusStopsSearchAdapter()
        busStopsRecyclerView.adapter =  busStopsSearchAdapter

        val busServicesRecyclerView: RecyclerView = view.findViewById(R.id.busServices_recycler_view)
        busServicesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val busServicesAdapter = BusServicesSearchAdapter()
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
}