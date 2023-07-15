package com.buffkatarina.busarrival.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffkatarina.busarrival.data.BusTimings
import kotlinx.coroutines.launch


class BusApiViewModel: ViewModel() {
    private val _busTimings  = MutableLiveData<BusTimings>()
    val busTimings: LiveData<BusTimings>
        get() = _busTimings

    private val busApiService = BusApiService.BusApi.busApi

    fun getBusTimings(busStopCode: String?){
        /*

        Get a list of bus timings for a given bus stop code
        Updates the busTimings class variable with the result
        List of bus timings can be obtained by 'OBSERVING' the busTimings variable for changes
        * */
        viewModelScope.launch{
            try {
                _busTimings.value = busApiService.getBusTimings(busStopCode)
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")

            }

        }
    }

    fun getBusStops(){
        /*
        Parses information of all bus stops currently being serviced by
        buses, including: Bus Stop Code, location coordinates into a database
        */
        viewModelScope.launch {
            /*TODO*/
        }
    }
}