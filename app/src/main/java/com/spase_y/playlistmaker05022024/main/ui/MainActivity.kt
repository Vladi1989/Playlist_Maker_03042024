package com.spase_y.playlistmaker05022024.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.mediateka.ui.presentation.MediatekaFragment
import com.spase_y.playlistmaker05022024.search.ui.presentation.SearchFragment
import com.spase_y.playlistmaker05022024.settings.ui.presentation.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val settings = findViewById<TextView>(R.id.setting)
//        settings.setOnClickListener {
//            startActivity(Intent(this, SettingsActivity::class.java))
//        }
//        val mediateka = findViewById<TextView>(R.id.mediateka)
//        mediateka.setOnClickListener {
//            startActivity(Intent(this, LibraryActivity::class.java))
//        }
//        val find = findViewById<TextView>(R.id.find)
//        find.setOnClickListener {
//            startActivity(Intent(this, SearchActivity::class.java))
//        }
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
            MediatekaFragment()
        ).commit()
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener {
            item ->
            when (item.itemId){
                R.id.search_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
                        SearchFragment()
                    ).commit()
                    true
                }
                R.id.settings_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
                        SettingsFragment()
                    ).commit()
                true
                }
                R.id.mediateka_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
                        MediatekaFragment()
                    ).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}



