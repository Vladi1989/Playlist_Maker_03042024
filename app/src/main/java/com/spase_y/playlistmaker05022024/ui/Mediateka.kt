package com.spase_y.playlistmaker05022024.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.spase_y.playlistmaker05022024.R

class Mediateka : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediateka)
        val arrowBack = findViewById<ImageButton>(R.id.buttonBack)
        arrowBack.setOnClickListener {
            finish()
        }
    }
}
