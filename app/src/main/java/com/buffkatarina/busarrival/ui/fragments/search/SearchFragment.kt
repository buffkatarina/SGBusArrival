package com.buffkatarina.busarrival.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.bus_routes.BusRoutesFragment
import com.buffkatarina.busarrival.ui.fragments.bus_timings.BusTimingFragment

class SearchFragment : Fragment(),
    BusServicesSearchAdapter.ToBusRoutes,
    BusStopsSearchAdapter.ToBusTimings {
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
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
//      Set up recycler view for bus stops
        val busStopsRecyclerView: RecyclerView = view.findViewById(R.id.busStops_recycler_view)
        busStopsRecyclerView.layoutManager = LinearLayoutManager(context)
        val busStopsSearchAdapter = BusStopsSearchAdapter(this)
        busStopsRecyclerView.adapter = busStopsSearchAdapter

        //      Set up recycler view for bus services
        val busServicesRecyclerView: RecyclerView =
            view.findViewById(R.id.busServices_recycler_view)
        busServicesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val busServicesAdapter = BusServicesSearchAdapter(this)
        busServicesRecyclerView.adapter = busServicesAdapter

        model.searchQuery.observe(viewLifecycleOwner) { query ->
            model.searchBusStops(query).observe(viewLifecycleOwner) { data ->
                busStopsSearchAdapter.updateData(data)
            }
            model.searchBusServices(query).observe(viewLifecycleOwner) { data ->
                busServicesAdapter.updateData(data)
            }
        }
    }

    override fun toBusRoutes(query: String) {
        /*Displays bus routes fragments on the screen
        * Implementation for ToBusTimings interface in BusStopsSearchAdapter
        * */
        model.setBusServiceNo(query)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, BusRoutesFragment(), "BusRoutesFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun toBusTimings(query: String) {
        /*Displays bus routes fragments on the screen
      * Implementation for ToBusRoutes interface in BusServicesSearchAdapter
      * */
        model.setBusStopCode(query)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, BusTimingFragment(), "BusTimingFragment")
            .addToBackStack(null)
            .commit()
    }
}