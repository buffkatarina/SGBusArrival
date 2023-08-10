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


class ActivityViewModel(application: Application) : AndroidViewModel(application) {
    /*View model that handles all the HTTP requests and parsing and building of database.*/


    //store all the retrieved bus timings from the api
    private val _busTimings = MutableLiveData<BusTimings>()
    private val busTimings: LiveData<BusTimings> get() = _busTimings

    //store search query from the search view
    private val _searchQuery = MutableLiveData<String?>()
    val searchQuery: LiveData<String?> get() = _searchQuery

    //store the retrieved list of bus routes after matching each bus stop with the description
    private val _busRoutesList = MutableLiveData<List<List<BusRoutesFiltered>>>()
    val busRoutesList: LiveData<List<List<BusRoutesFiltered>>> get() = _busRoutesList

    //store favourite bus services at a specified stop
    private val _favouriteBusServices = MutableLiveData<List<String>>()
    private val favouriteBusServices: LiveData<List<String>> get() = _favouriteBusServices

    //database building progress - true when buildDB() is completed
    private val _databaseState = mutableStateOf(false)
    val databaseState: State<Boolean> = _databaseState

    //button clicked from dialog
    private val _dialogState = mutableStateOf(false)
    val dialogState: State<Boolean> = _dialogState

    private var _busStopCode = MutableLiveData<String?>()
    val busStopCode: LiveData<String?> = _busStopCode

    private val _buildDate = MutableLiveData(BuildDate(9, ""))
    val buildDate: LiveData<BuildDate?> = _buildDate

    private val _busStops = MutableLiveData<List<BusStops.BusStopData>>()
    val busStops: LiveData<List<BusStops.BusStopData>> = _busStops

    private val busApiRepository: BusApiRepository
    private val busArrivalRepository: BusArrivalRepository

    init {
        val dbDao = BusArrivalDatabase.getInstance(application).BusArrivalDao()
        busArrivalRepository = BusArrivalRepository(dbDao)
        val busApiInterface = BusApiService.BusApi.busApi
        busApiRepository = BusApiRepository(busApiInterface)
    }

    val setBusStopCode = {code: String? -> _busStopCode.value = code}
    fun setDialogState(bool: Boolean) {
        _dialogState.value = bool
    }

    fun setDatabaseState(bool: Boolean) {
        _databaseState.value = bool
    }

    suspend fun getBusTimingsByServiceNo(busStopCode: String?, serviceNo: String): BusTimings? {
        /*Gets a singular record of BusTimings. Requires both bus stop code and the service number*/
        return try {
            busApiRepository.getBusTimingsByServiceNo(busStopCode, serviceNo)
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")
            null
        }
    }

    suspend fun getBusTimings(busStopCode: String?) {
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

    fun getBusRoutes(searchQuery: String?) {
        /*Updates busRoutesList variable with the queried bus routes */
        viewModelScope.launch(Dispatchers.IO) {
            val direction1 = busArrivalRepository.searchBusRoutes(searchQuery, "1")
            val direction2 = busArrivalRepository.searchBusRoutes(searchQuery, "2")
            _busRoutesList.postValue(listOf(direction1, direction2))
        }
    }

    fun buildDB() {
        /*
        Loads all parsed data into the database
        */
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertBusStops()
                insertBusServices()
                insertBusRoutes()
                launch(Dispatchers.Main) {
                    _databaseState.value = true
                }

            } catch (e: Exception) {
                Log.d("GetBusStopsError", "${e.message}")
            }
        }
    }

    private suspend fun insertBusStops() {
        /*Parse all bus stops and insert into database*/

        var skipAmt = 0
        do {
            val busStops = busApiRepository.getBusStops(skipAmt)
            for (i in busStops.data) {
                busArrivalRepository.insertBusStops(i)
            }
            skipAmt += 500
        } while (busApiRepository.getBusStops(skipAmt).data.isNotEmpty())

    }

    private suspend fun insertBusServices() {
        /*Parse all bus services and insert into database*/
        var skipAmt = 0
        do {
            val busServices = busApiRepository.getBusServices(skipAmt)
            for (i in busServices.data) {
                busArrivalRepository.insertBusServices(i)
            }
            skipAmt += 500
        } while (busApiRepository.getBusServices(skipAmt).data.isNotEmpty())

    }

    private suspend fun insertBusRoutes() {
        /*Parse all bus routes and insert into database*/
        var skipAmt = 0
        do {
            val busRoutes = busApiRepository.getBusRoutes(skipAmt)
            for (i in busRoutes.data) {
                busArrivalRepository.insertBusRoutes(i)
            }
            skipAmt += 500
        } while (busApiRepository.getBusRoutes(skipAmt).data.isNotEmpty())

    }

    fun insertFavouriteBusService(busStopCode: String, serviceNo: String) {
        /*Inserts newly favourite bus service
        * Arguments: bus stop code -> Integer
                     service number -> string
                     * */
        viewModelScope.launch(Dispatchers.IO) {
            busArrivalRepository.insertFavouriteBusService(
                FavouriteBusServices(
                    busStopCode,
                    serviceNo
                )
            )
        }
    }

    fun removeFavouriteBusService(busStopCode: String, serviceNo: String) {
        /*Remove a favourite bus service record
          Arguments: bus stop code -> Integer
                     service number -> string
         */
        viewModelScope.launch(Dispatchers.IO) {
            busArrivalRepository.removeFavouriteBusService(busStopCode, serviceNo)
        }
    }

    fun getFavouriteBusServices(busStopCode: String) {
        //Get list of favourite bus services after filtered against bus stop code
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteBusServices.postValue(busArrivalRepository.getFavouriteBusService(busStopCode))
        }
    }

    fun getBuildDate() {
        /*
        * Gets build date from the BuildDate table
        * */
        viewModelScope.launch(Dispatchers.IO) {
            _buildDate.postValue(busArrivalRepository.getBuildDate())
        }
    }


    fun insertBuildDate(date: String) {
        /*
        * Inserts specified parameter into BuildDate table
        * */
        viewModelScope.launch(Dispatchers.IO) {
            busArrivalRepository.insertBuildDate(date)
        }
    }

    fun getAllBusStops() {
        viewModelScope.launch(Dispatchers.IO) {
            _busStops.postValue(busArrivalRepository.getAllBusStops())
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

    class MergedLiveData<F, S>(first: LiveData<F>, second: LiveData<S>) :
        MediatorLiveData<Pair<F?, S?>>() {
        init {
            addSource(first) { firstData -> value = Pair(firstData, second.value) }
            addSource(second) { secondData -> value = Pair(first.value, secondData) }
        }
    }
}