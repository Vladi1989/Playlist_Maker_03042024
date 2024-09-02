package com.spase_y.playlistmaker05022024.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.mediateka.root.ui.fragment.MediatekaFragment
import com.spase_y.playlistmaker05022024.search.ui.presentation.SearchFragment
import com.spase_y.playlistmaker05022024.settings.ui.presentation.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView,
                MediatekaFragment()
            ).commit()
            bottomNavigationView.selectedItemId = R.id.mediateka_fragment
        } else {
            val selectedItemId =
                savedInstanceState.getInt("selectedItemId", R.id.mediateka_fragment)
            bottomNavigationView.selectedItemId = selectedItemId
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search_fragment -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView,
                        SearchFragment()
                    ).commit()
                    true
                }

                R.id.settings_fragment -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView,
                        SettingsFragment()
                    ).commit()
                    true
                }

                R.id.mediateka_fragment -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView,
                        MediatekaFragment()
                    ).commit()
                    true
                }

                else -> false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", bottomNavigationView.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedItemId = savedInstanceState.getInt("selectedItemId", R.id.mediateka_fragment)
        bottomNavigationView.selectedItemId = selectedItemId
    }
    fun showBottomNavigation(){
        bottomNavigationView.visibility = View.VISIBLE
    }
    fun hideBottomNavigation(){
        bottomNavigationView.visibility = View.GONE
    }

}
