package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.BusApiViewModel
import kotlinx.coroutines.launch

class BusTimingFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.bus_timings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.busTimings_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        getBusTimings(recyclerView)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBusTimings(recyclerView: RecyclerView)
    /*Gets bus timings and loads them into the recycler view*/
    {
        val viewModel =ViewModelProvider(requireActivity())[BusApiViewModel::class.java]
        parentFragmentManager.setFragmentResultListener("busStopCodeKey",
            viewLifecycleOwner) {
                _, bundle ->
            val busStopCode =  bundle.getString("busStopCode")?.toInt()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getBusTimings(busStopCode)
            }
        }
        viewModel.busTimings.observe(viewLifecycleOwner) {
                busTimings ->
            recyclerView.adapter = BusArrivalAdapter(busTimings)

        }
    }


}