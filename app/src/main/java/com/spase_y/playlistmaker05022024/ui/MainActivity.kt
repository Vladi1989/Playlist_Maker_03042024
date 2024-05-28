package com.spase_y.playlistmaker05022024.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.spase_y.playlistmaker05022024.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings = findViewById<TextView>(R.id.setting)
        settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        val mediateka = findViewById<TextView>(R.id.mediateka)
        mediateka.setOnClickListener {
            startActivity(Intent(this, Mediateka::class.java))
        }
        val find = findViewById<TextView>(R.id.find)
        find.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}



