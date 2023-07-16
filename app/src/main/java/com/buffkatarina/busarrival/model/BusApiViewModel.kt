package com.buffkatarina.busarrival.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.buffkatarina.busarrival.api.BusApiRepository
import com.buffkatarina.busarrival.api.BusApiService
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.db.BusArrivalDatabase
import com.buffkatarina.busarrival.data.db.BusArrivalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BusApiViewModel(application: Application): AndroidViewModel(application) {
    /*View model that handles all the HTTP requests and parsing and building of database.*/

    private val _busTimings  = MutableLiveData<BusTimings>()
    val busTimings: LiveData<BusTimings>
        get() = _busTimings

    private val busApiRepository: BusApiRepository
    private val busArrivalRepository: BusArrivalRepository

    init {
        val dbDao = BusArrivalDatabase.getInstance(application).BusArrivalDao()
        busArrivalRepository = BusArrivalRepository(dbDao)

        val busApiInterface = BusApiService.BusApi.busApi
        busApiRepository = BusApiRepository(busApiInterface)
    }
    fun getBusTimings(busStopCode: String?){
        /*

        Get a list of bus timings for a given bus stop code
        Updates the busTimings class variable with the result
        List of bus timings can be obtained by 'OBSERVING' the busTimings variable for changes
        * */
        viewModelScope.launch{
            try {
                _busTimings.value = busApiRepository.getBusTimings(busStopCode)
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")

            }

        }
    }

    fun storeBusStops(){
        /*
        Parses information of all bus stops currently being serviced by
        buses, including: Bus Stop Code, location coordinates into a sql database
        */

        viewModelScope.launch(Dispatchers.IO) {
            var skipAmt = 0
            try{
                do{
                    val busStops = busApiRepository.getBusStops(skipAmt)
                    for (i in busStops.data){
                            busArrivalRepository.insertBusStops(i)
                        Log.i("ASDASD", i.toString())
                    }
                    skipAmt += 500

                } while(busApiRepository.getBusStops(skipAmt).data.isNotEmpty())

            } catch (e: Exception){
                Log.d("GetBusStopsError", "${e.message}")
            }
        }
    }
}