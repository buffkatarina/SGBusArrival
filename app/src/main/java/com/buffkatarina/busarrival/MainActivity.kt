package com.buffkatarina.busarrival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.HomeFragment
import com.buffkatarina.busarrival.ui.fragments.search.SearchFragment

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(this)[ActivityViewModel::class.java]
    }

    private val homeFragment by lazy {
        HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, homeFragment, "HomeFragment")
            .commit()
        model.buildDB()
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val search = menu.findItem(R.id.search)
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
        model.clearSearchHandler.observe(this) {result ->
            if (result) {
                model.setSearchQuery(null)
                model.clearSearchQuery(false)

            }
        }


        if (query?.isNotEmpty() == true) {
            Log.i("ASD", query)
            model.setSearchQuery("$query%")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                if (supportFragmentManager.findFragmentByTag("SearchFragment") == null) {
                    if (homeFragment.isVisible) {
                        supportFragmentManager.beginTransaction()
                            .hide(homeFragment)
                            .add(R.id.fragmentHolder, SearchFragment())
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






