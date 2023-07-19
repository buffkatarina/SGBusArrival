package com.buffkatarina.busarrival.ui.fragments.bus_routes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.ActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusRoutesFragment: Fragment() {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bus_routes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getBusRoutes()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBusRoutes() {
        parentFragmentManager.setFragmentResultListener("query", viewLifecycleOwner) {
            _, bundle ->
            val query = bundle.getString("query")
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                for (i in model.searchBusRoutes(query)) {
                    Log.i("Kntl", i.toString())
                }

            }
        }
    }
}