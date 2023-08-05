package com.buffkatarina.busarrival

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.home.HomeFragment
import com.buffkatarina.busarrival.ui.fragments.search.SearchFragment
import org.osmdroid.config.Configuration
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.time.DurationUnit


class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(this)[ActivityViewModel::class.java]
    }

    private val homeFragment by lazy {
        HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDatabase()
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        requestPermission()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, homeFragment, "HomeFragment")
            .commit()
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun requestPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {

                }
            }
        when {
            ContextCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            else -> {
              requestPermissionLauncher.launch(
                  ACCESS_FINE_LOCATION
              )
            }
        }
    }


    private fun setUpDatabase() {
        model.getBuildDate()
        val currentDate = LocalDate.now()
        model.buildDate.observe(this) { date ->
            if (date?.id != 9) { // ignore first value of buildDate
                if (date == null) {
                    model.buildDB()
                    model.insertBuildDate(currentDate.toString())
                }
                else {
                    val parsedDate =  LocalDate.parse(date.buildDate)
                    //Builds database every 3 days
                    if (ChronoUnit.DAYS.between(parsedDate, currentDate)> 3) {
                        model.buildDB()
                        model.insertBuildDate(currentDate.toString())
                    }

                    else {
                        model.setDatabaseState(true)
                        model.setDialogState(true)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        //unfinished implementation
        supportFragmentManager.popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val search = menu.findItem(R.id.search)
        search.icon?.setTint(ContextCompat.getColor(applicationContext, R.color.black))
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        searchView?.isIconified = false
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        //unfinished
        if (query?.isNotEmpty() == true) {
            model.clearSearchQuery(false) //Start loading new query after input is obtained
            model.setSearchQuery("$query%")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //unfinished
        when (item.itemId) {
            R.id.search -> {
                if (supportFragmentManager.findFragmentByTag("SearchFragment") == null) {
                    if (homeFragment.isVisible) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentHolder, SearchFragment())
                            .addToBackStack(null)
                            .commit()
                    }

                    supportFragmentManager.findFragmentByTag("BusTimingFragment")
                        ?.let { busTimingFragment ->
                            if (busTimingFragment.isVisible) {
                                supportFragmentManager.beginTransaction()
                                    .hide(busTimingFragment)
                                    .add(R.id.fragmentHolder, SearchFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                }
                return true
            }
        }
        return false
    }

}






