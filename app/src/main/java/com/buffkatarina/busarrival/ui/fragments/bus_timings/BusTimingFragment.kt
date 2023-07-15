package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.api.BusApiViewModel

class BusTimingFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.bus_timings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = BusApiViewModel()
        val recyclerView = view.findViewById<RecyclerView>(R.id.busTimings_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        parentFragmentManager.setFragmentResultListener("busStopCodeKey",
            viewLifecycleOwner) {
                _, bundle ->
            val busStopCode =  bundle.getString("busStopCode")

            viewModel.getBusTimings(busStopCode)
        }
        viewModel.busTimings.observe(viewLifecycleOwner) {
            busTimings ->
            recyclerView.adapter = BusArrivalAdapter(busTimings)

        }


        super.onViewCreated(view, savedInstanceState)
    }


}