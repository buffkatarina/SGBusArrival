package com.buffkatarina.busarrival.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.db.BusArrivalDatabase
import com.buffkatarina.busarrival.ui.fragments.bus_timings.BusTimingFragment

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentFragment = parentFragmentManager.findFragmentByTag("HomeFragment")!!
        val editText = view.findViewById<EditText>(R.id.editText)
        val button = view.findViewById<Button>(R.id.button)
        val busTimingFragment = BusTimingFragment()
        button.setOnClickListener {
            val busStopCode = editText.text.toString()
            parentFragmentManager.setFragmentResult("busStopCodeKey"
                , bundleOf("busStopCode" to busStopCode))
            parentFragmentManager.beginTransaction()
                .hide(currentFragment)
                .addToBackStack(null)
                .attach(busTimingFragment)
                .add(R.id.fragmentHolder, busTimingFragment, "BusTimingFragment")
                .commit()
        }
        super.onViewCreated(view, savedInstanceState)
    }


}