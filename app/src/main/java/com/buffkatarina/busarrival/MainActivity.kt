package com.buffkatarina.busarrival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buffkatarina.busarrival.api.BusApiService.BusApi.busApi
import com.buffkatarina.busarrival.ui.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, HomeFragment(), "HomeFragment")
            .commit()

    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }


}

