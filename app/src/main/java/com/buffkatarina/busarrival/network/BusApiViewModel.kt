    package com.buffkatarina.busarrival.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffkatarina.busarrival.network.BusApi
import kotlinx.coroutines.launch


class BusApiViewModel: ViewModel() {
     fun getBusRoutes(busStopCode: String){
        viewModelScope.launch{
            try {
                val result = BusApi.busApi.getBusTimings(busStopCode)
                Log.d("ASD", result.toString())
            } catch (e: Exception) {
                Log.d("Error??", "${e.message}")

            }

        }
    }

    fun getBusTimings(){
        viewModelScope.launch {
            /*TODO*/
        }
    }
}