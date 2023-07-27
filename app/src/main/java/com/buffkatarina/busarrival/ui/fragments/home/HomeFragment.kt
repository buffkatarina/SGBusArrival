package com.buffkatarina.busarrival.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.home.favourites.Favourites


class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val composeView: androidx.compose.ui.platform.ComposeView = view.findViewById(R.id.compose_view)
        val viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]

        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val databaseBuildState = viewModel.databaseState
                DbDialog(databaseBuildState.value)
                if (databaseBuildState.value) {
                    val favourites = viewModel.getAllFavouriteBusService().observeAsState()
                    val timings = viewModel.favouriteBusTimings.observeAsState()
                    viewModel.getFavouriteBusTimings()
                    if (favourites.value != null && timings.value?.isNotEmpty() == true) {
                        Favourites(favourites = favourites.value!!, timings = timings.value!! )
                    }
                }


            }
        }
        return view
    }

}