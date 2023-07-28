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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.home.favourites.Favourites
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val composeView: androidx.compose.ui.platform.ComposeView = view.findViewById(R.id.compose_view)
        val viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        val databaseBuildState by viewModel.databaseState

        if (databaseBuildState) {
            viewLifecycleOwner.lifecycleScope.launch {
                while (true) {
                    //get new bus timings every 1 minute
                    viewModel.getFavouriteBusTimings()
                    delay(60000)
                }
            }
        }

        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DbDialog(databaseBuildState)
                if (databaseBuildState) {
                    val favourites by  viewModel.getAllFavouriteBusService().observeAsState()
                    val timings by viewModel.favouriteBusTimings.observeAsState()
                    if (favourites != null && timings?.isNotEmpty() == true) {
                        Favourites(favourites = favourites!!, timings = timings!! )
                    }
                }
            }
        }
        return view
    }

}