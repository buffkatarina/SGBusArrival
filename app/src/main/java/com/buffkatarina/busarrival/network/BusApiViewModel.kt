    package com.buffkatarina.busarrival.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffkatarina.busarrival.data.BusTimings
import com.buffkatarina.busarrival.network.BusApi
import kotlinx.coroutines.launch


class BusApiViewModel: ViewModel() {
    private val _busTimings  = MutableLiveData<BusTimings>()
    val busTimings: LiveData<BusTimings>
        get() = _busTimings

    fun getBusRoutes(busStopCode: String?){
        viewModelScope.launch{
            try {
                _busTimings.value = BusApi.busApi.getBusTimings(busStopCode)
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