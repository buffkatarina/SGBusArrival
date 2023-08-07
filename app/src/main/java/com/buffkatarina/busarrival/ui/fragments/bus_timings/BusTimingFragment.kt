package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BusTimingFragment : Fragment(), BusTimingsAdapter.FavouritesHandler {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.bus_timings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView(view)
    }

    private fun setUpView(view: View)
            /*
            Set ups recycler view
            Gets bus timings and loads them into the recycler view*/ {
        val recyclerView: RecyclerView = view.findViewById(R.id.busTimings_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val currentFragment = this
        val viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        parentFragmentManager.setFragmentResultListener(
            "busStopCodeKey",
            viewLifecycleOwner
        ) { _, bundle ->
            //Gets the queried bus stop code
            val busStopCode = bundle.getString("busStopCode")?.toInt()
            (requireActivity() as AppCompatActivity).supportActionBar?.title =
                busStopCode.toString()
            busStopCode?.let { code ->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getFavouriteBusServices(busStopCode)
                    val adapter = BusTimingsAdapter(code, currentFragment)
                    recyclerView.adapter = adapter

                    //Merge data from view model and load into recycler view
                    viewModel.mergeFavouriteAndTimings().observe(viewLifecycleOwner) { result ->
                        val favouriteBusServices = result.first
                        val busTimings = result.second
                        if (favouriteBusServices != null && busTimings != null) {
                            adapter.updateTimings(busTimings.services)
                            adapter.updateFavourites(favouriteBusServices)
                        }
                    }
                    while (true) {
                        //Retrieve new bus timings every 1 minute
                        viewModel.getBusTimings(busStopCode)
                        delay(60000)
                    }

                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.default_title)
    }


    override fun addFavouriteBusService(busStopCode: Int, serviceNo: String) {
        model.insertFavouriteBusService(busStopCode, serviceNo)

    }

    override fun removeFavouriteBusService(busStopCode: Int, serviceNo: String) {
        model.removeFavouriteBusService(busStopCode, serviceNo)
    }

    override fun getColor(color: Int): Int {
        return ContextCompat.getColor(requireContext(), color)
    }
}
