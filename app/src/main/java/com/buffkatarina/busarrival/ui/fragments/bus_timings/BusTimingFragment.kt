package com.buffkatarina.busarrival.ui.fragments.bus_timings

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.switchMap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.isNull
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.bus_routes.BusRoutesFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BusTimingFragment : Fragment(), BusTimingsAdapter.FragmentCallback {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    private var mBusStopCode: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.bus_timings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setUpView(view)
    }

    private fun setUpView(view: View)
            /*
            Set ups recycler view
            Gets bus timings and loads them into the recycler view*/ {
        lateinit var adapter: BusTimingsAdapter
        val recyclerView: RecyclerView = view.findViewById(R.id.busTimings_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val currentFragment = this
        val viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        val gestureDetector = object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        }
        viewModel.busStopCode.switchMap {
            it?.let {
                viewLifecycleOwner.lifecycleScope.launch {
                    while (true) {
                        //Retrieve new bus timings every 1 minute
                        viewModel.getBusTimings(it)
                        delay(60000)
                    }
                }
                (requireActivity() as AppCompatActivity).supportActionBar?.title = it
                mBusStopCode = it
                viewModel.getFavouriteBusServices(it)
                adapter = BusTimingsAdapter(
                    it,
                    currentFragment,
                    GestureDetector(context, gestureDetector)
                )
                recyclerView.adapter = adapter
                viewModel.mergeFavouriteAndTimings()
            }
        }.observe(viewLifecycleOwner) {
            val favouriteBusServices = it.first
            val busTimings = it.second
            if (!isNull(busTimings, favouriteBusServices)) {
                adapter.updateTimings(busTimings!!.services)
                adapter.updateFavourites(favouriteBusServices!!)
            }
        }
    }

    override fun addFavouriteBusService(busStopCode: String, serviceNo: String) {
        model.insertFavouriteBusService(busStopCode, serviceNo)

    }

    override fun removeFavouriteBusService(busStopCode: String, serviceNo: String) {
        model.removeFavouriteBusService(busStopCode, serviceNo)
    }

    override fun getColor(color: Int): Int {
        return ContextCompat.getColor(requireContext(), color)
    }

    override fun toBusRoutes(query: String?) {
        model.setBusServiceNo(query)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, BusRoutesFragment(), "BusRoutesFragment")
            .addToBackStack(null)
            .commit()
    }
}
