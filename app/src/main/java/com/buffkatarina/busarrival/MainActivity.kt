package com.buffkatarina.busarrival

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

