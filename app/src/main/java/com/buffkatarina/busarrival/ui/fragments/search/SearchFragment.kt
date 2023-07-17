package com.buffkatarina.busarrival.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.model.BusApiViewModel

class SearchFragment: Fragment() {
    private val model: BusApiViewModel by lazy {
        ViewModelProvider(requireActivity())[BusApiViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.searchFragment_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val searchAdapter = SearchAdapter()
        recyclerView.adapter = searchAdapter
        model.searchQuery.observe(viewLifecycleOwner) { query ->
            Log.i("ASD", query)
            model.searchBusStops(query).observe(viewLifecycleOwner) {data ->
                searchAdapter.updateData(data)
            }
        }

    }
}