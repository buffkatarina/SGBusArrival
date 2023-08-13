package com.buffkatarina.busarrival

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.home.HomeFragment
import com.buffkatarina.busarrival.ui.fragments.search.SearchFragment
import com.buffkatarina.busarrival.ui.fragments.search.SearchView
import org.osmdroid.config.Configuration
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(this)[ActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDatabase()
        Configuration.getInstance().load(
            this,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        )
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, HomeFragment(), "HomeFragment")
            .commit()
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(true)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (supportFragmentManager.findFragmentByTag("HomeFragment")?.isVisible == true) {
                finish()
            } else if (supportFragmentManager.findFragmentByTag("SearchFragment")?.isVisible == true) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentHolder, HomeFragment(), "HomeFragment")
                    .commit()
            } else if (supportFragmentManager.findFragmentByTag("BusTimingFragment")?.isVisible == true) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentHolder, SearchFragment(), "SearchFragment")
                    .commit()
            } else {
                supportFragmentManager.popBackStack()
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
                } else {
                    val parsedDate = LocalDate.parse(date.buildDate)
                    //Builds database every 3 days
                    if (ChronoUnit.DAYS.between(parsedDate, currentDate) > 3) {
                        model.buildDB()
                        model.insertBuildDate(currentDate.toString())
                    } else {
                        model.setDatabaseState(true)
                        model.setDialogState(true)
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val search = menu.findItem(R.id.search)
        search.icon?.setTint(ContextCompat.getColor(applicationContext, R.color.black))
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        //Closes search immediately on single back press
        searchView?.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                search.collapseActionView()
            }
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {

        //unfinished
        query?.let {
            model.setSearchQuery("$it%")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                if (supportFragmentManager.findFragmentByTag("SearchFragment")?.isVisible != true) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentHolder, SearchFragment(), "SearchFragment")
                        .commit()
                }

                true
            }

            else -> {
                false
            }

        }

    }
}






