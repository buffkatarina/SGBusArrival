package com.buffkatarina.busarrival.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.home.favourites.Favourites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment: Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    private val favouriteTimings = MutableLiveData<MutableList<BusTimings>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val composeView: androidx.compose.ui.platform.ComposeView =
            view.findViewById(R.id.compose_view)
//        val databaseBuildState by viewModel.databaseState
        val databaseBuildState = true
        if (databaseBuildState) {
                //Get all the favourite bus services records from the database
            viewModel.getAllFavouriteBusServices().observe(viewLifecycleOwner) { result ->
                parseBusTimings(result)
                viewLifecycleOwner.lifecycleScope.launch {
                    while (true) {
                        //get new bus timings every 1 minute
                        delay(60000)
                    }
                }
            }
        }

        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DbDialog(databaseBuildState)
                if (databaseBuildState) {
                    val favourites by viewModel.getAllFavouriteBusServices().observeAsState()
                    val timings by favouriteTimings.observeAsState()
                    if (favourites != null && timings != null) {
                        Favourites((favourites to timings) as Pair<List<FavouriteBusServicesWithDescription>, MutableList<BusTimings>>)
                    }
                }
            }
        }
        return view
    }

    private fun parseBusTimings(result: List<FavouriteBusServicesWithDescription>) {
        /*Adds bus timings for favourite bus services to a favourite timings list */
        val mutableList =  mutableListOf<BusTimings>()
        viewLifecycleOwner.lifecycleScope.launch {
            for (favourite in result) {
                viewModel.getBusTimingsByServiceNo(favourite.busStopCode, favourite.serviceNo)
                viewModel.busTimings.observe(viewLifecycleOwner) { timings->
                    mutableList.add(timings)
                }
            }
            favouriteTimings.value = mutableList
        }
    }
}