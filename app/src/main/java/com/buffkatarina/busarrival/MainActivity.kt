package com.buffkatarina.busarrival

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.buffkatarina.busarrival.model.BusApiViewModel
import com.buffkatarina.busarrival.ui.fragments.HomeFragment
import com.buffkatarina.busarrival.ui.fragments.bus_timings.BusTimingFragment
import com.buffkatarina.busarrival.ui.fragments.search.SearchFragment

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{
    private val model: BusApiViewModel by lazy {
        ViewModelProvider(this)[BusApiViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, HomeFragment(), "HomeFragment")
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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                supportFragmentManager.findFragmentByTag("HomeFragment")?.let { it ->
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .addToBackStack("HomeFragment")
                    .add(R.id.fragmentHolder, SearchFragment(), "SearchFragment")
                    .commit()
                }
                return true
            }
        }

        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            model.setSearchQuery("%$query%")
        }
        return true
    }

}






