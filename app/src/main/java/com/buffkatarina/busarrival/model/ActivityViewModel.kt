package com.buffkatarina.busarrival.model

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.buffkatarina.busarrival.api.BusApiRepository
import com.buffkatarina.busarrival.api.BusApiService
import com.buffkatarina.busarrival.data.db.BusArrivalDatabase
import com.buffkatarina.busarrival.data.db.BusArrivalRepository
import com.buffkatarina.busarrival.data.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActivityViewModel(application: Application): AndroidViewModel(application) {
    /*View model that handles all the HTTP requests and parsing and building of database.*/

    // signal to reset the search result
    private val _clearSearchHandler = MutableLiveData<Boolean>()
    val clearSearchHandler: LiveData<Boolean> get() = _clearSearchHandler

    //tore all the retrieved bus timings from the api
    private val _busTimings  = MutableLiveData<BusTimings>()
    val busTimings: LiveData<BusTimings> get() = _busTimings

    //store search query from the search view
    private val _searchQuery  = MutableLiveData<String?>()
    val searchQuery: LiveData<String?> get() = _searchQuery

    //store the retrieved list of bus routes after matching each bus stop with the description
    private val _busRoutesList = MutableLiveData<List<List<BusRoutesFiltered>>>()
    val busRoutesList: LiveData<List<List<BusRoutesFiltered>>> get() = _busRoutesList

    //store favourite bus services at a specified stop
    private val _favouriteBusServices = MutableLiveData<List<String>>()
    private val favouriteBusServices: LiveData<List<String>> get() = _favouriteBusServices

    //database building progress - true when buildDB() is completed
    private val _databaseState  = mutableStateOf(false)
    val databaseState: State<Boolean>  = _databaseState

    private val _allFavouriteBusServices = MutableLiveData<List<FavouriteBusServicesWithDescription>>()
    val allFavouriteBusServices: LiveData<List<FavouriteBusServicesWithDescription>> = _allFavouriteBusServices

    private val busApiRepository: BusApiRepository
    private val busArrivalRepository: BusArrivalRepository
    init {
        val dbDao = BusArrivalDatabase.getInstance(application).BusArrivalDao()
        busArrivalRepository = BusArrivalRepository(dbDao)
        val busApiInterface = BusApiService.BusApi.busApi
        busApiRepository = BusApiRepository(busApiInterface)
    }

     suspend fun getBusTimingsByServiceNo(busStopCode: Int?, serviceNo: String){
         /*Gets a singular record of BusTimings. Requires both bus stop code and the service number*/
         try {
             _busTimings.value = busApiRepository.getBusTimingsByServiceNo(busStopCode, serviceNo)
         } catch (e: Exception) {
             Log.d("Error", "${e.message}")

         }
     }

    suspend fun getBusTimings(busStopCode: Int?){
        /*

        Get a list of bus timings for a given bus stop code
        Updates the busTimings class variable with the result
        List of bus timings can be obtained by 'OBSERVING' the busTimings variable for changes
        * */
        try {
            _busTimings.value = busApiRepository.getBusTimings(busStopCode)
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")

        }
    }
    val clearSearchQuery = { bool: Boolean -> _clearSearchHandler.value = bool}

    fun setSearchQuery(query: String?) {
        _searchQuery.value = query
    }

    fun searchBusStops(searchQuery: String?): LiveData<List<BusStops.BusStopData>> {
        /*Returns a list of BusStops.BusStopData entities for matching bus stops as live data*/
        return busArrivalRepository.searchBusStops(searchQuery).asLiveData()
    }

    fun searchBusServices(searchQuery: String?): LiveData<List<String>> {
        /*Returns a list of string for matching bus services as live data*/
        return busArrivalRepository.searchBusServices(searchQuery).asLiveData()
    }

    fun getBusRoutes(searchQuery: String?){
        /*Updates busRoutesList variable with the queried bus routes */
        viewModelScope.launch(Dispatchers.IO) {
            val direction1 = busArrivalRepository.searchBusRoutes(searchQuery,"1" )
            val direction2  = busArrivalRepository.searchBusRoutes(searchQuery,"2" )
            launch(Dispatchers.Main) {
                _busRoutesList.value = listOf(direction1, direction2)
            }
        }
    }

    fun buildDB(){
        /*
        Loads all parsed data into the database
        */
        viewModelScope.launch(Dispatchers.IO) {
            try{
                insertBusStops()
                insertBusServices()
                insertBusRoutes()
                launch(Dispatchers.Main) {
                    _databaseState.value = true
                }

            } catch (e: Exception){
                Log.d("GetBusStopsError", "${e.message}")
            }
        }
    }

    private suspend fun insertBusStops() {
        /*Parse all bus stops and insert into database*/
        var skipAmt = 0
        do{
            val busStops = busApiRepository.getBusStops(skipAmt)
            for (i in busStops.data){
                busArrivalRepository.insertBusStops(i)
            }
            skipAmt += 500
        } while(busApiRepository.getBusStops(skipAmt).data.isNotEmpty())

    }

    private suspend fun insertBusServices() {
        /*Parse all bus services and insert into database*/
        var skipAmt = 0
        do{
            val busServices = busApiRepository.getBusServices(skipAmt)
            for (i in busServices.data){
                busArrivalRepository.insertBusServices(i)
            }
            skipAmt += 500
        } while(busApiRepository.getBusServices(skipAmt).data.isNotEmpty())

    }

    private suspend fun insertBusRoutes() {
        /*Parse all bus routes and insert into database*/
        var skipAmt = 0
        do{
            val busRoutes = busApiRepository.getBusRoutes(skipAmt)
            for (i in busRoutes.data){
                busArrivalRepository.insertBusRoutes(i)
            }
            skipAmt += 500
        } while(busApiRepository.getBusRoutes(skipAmt).data.isNotEmpty())

    }

     fun insertFavouriteBusService(busStopCode: Int, serviceNo: String) {
        /*Inserts newly favourite bus service
        * Arguments: bus stop code -> Integer
                     service number -> string
                     * */
         viewModelScope.launch(Dispatchers.IO) {
             busArrivalRepository.insertFavouriteBusService(FavouriteBusServices(busStopCode, serviceNo))
         }
    }

    fun removeFavouriteBusService(busStopCode: Int, serviceNo: String) {
        /*Remove a favourite bus service record
          Arguments: bus stop code -> Integer
                     service number -> string
         */
        viewModelScope.launch(Dispatchers.IO) {
            busArrivalRepository.removeFavouriteBusService(busStopCode, serviceNo)
        }
    }

    fun getFavouriteBusServices(busStopCode: Int) {
        //Get list of favourite bus services after filtered against bus stop code
        viewModelScope.launch(Dispatchers.IO){
           val favouriteBusServices = busArrivalRepository.getFavouriteBusService(busStopCode)
            launch(Dispatchers.Main){
                _favouriteBusServices.value = favouriteBusServices
            }
        }
    }

     @JvmName("getAllFavouriteBusServices1")
     fun getAllFavouriteBusServices(): LiveData<List<FavouriteBusServicesWithDescription>> {
        return busArrivalRepository.getAllFavouriteBusServices().asLiveData()
    }

    //Combines favouriteBusServices and BusTimings for usage by BusTimingFragment
    fun mergeFavouriteAndTimings(): MergedLiveData<List<String>, BusTimings> {
        return MergedLiveData(favouriteBusServices, busTimings)
    }

    class MergedLiveData<F, S>(first: LiveData<F>, second: LiveData<S>): MediatorLiveData<Pair<F?, S?>>() {
        init {
            addSource(first) {firstData -> value = Pair(firstData, second.value)}
            addSource(second) {secondData -> value = Pair(first.value, secondData)}
        }
    }
}