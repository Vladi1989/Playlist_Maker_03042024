package com.spase_y.playlistmaker05022024

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

        if (currentTrackItem != null){
            val ivIcon = findViewById<ImageView>(R.id.ivIcon)
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvArtist = findViewById<TextView>(R.id.tvArtistName)
            val tvDuration = findViewById<TextView>(R.id.tvDuration)
            val tvYear = findViewById<TextView>(R.id.tvYear)
            val tvGenre = findViewById<TextView>(R.id.tvGenre)
            val tvCountry = findViewById<TextView>(R.id.tvCountry)
            val tvAlbomName = findViewById<TextView>(R.id.tvAlbomName)
            val llAlbom = findViewById<LinearLayout>(R.id.llAlbom)
            val llYear = findViewById<LinearLayout>(R.id.llYear)
            val llGenre = findViewById<LinearLayout>(R.id.llGenre)
            val llCountry = findViewById<LinearLayout>(R.id.llCountry)
            tvName.text = currentTrackItem!!.trackName
            tvArtist.text = currentTrackItem!!.artistName
            tvDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentTrackItem!!.trackTimeMillis)
            val big = currentTrackItem!!.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
            Glide.with(this)
                .load(big)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivIcon)

            if (currentTrackItem!!.releaseDate.isNullOrEmpty()){
                llYear.visibility = View.GONE
            }
            else{
                tvYear.text = currentTrackItem!!.releaseDate.replaceAfter("-","").replace("-","")
            }
            if (currentTrackItem!!.collectionName.isNullOrEmpty()){
               llAlbom.visibility = View.GONE
            }
            else{
                tvAlbomName.text = currentTrackItem!!.collectionName
            }
            if(currentTrackItem!!.primaryGenreName.isNullOrEmpty()) {
                llGenre.visibility = View.GONE
            }
            else {
                tvGenre.text = currentTrackItem!!.primaryGenreName
            }
            if (currentTrackItem!!.country.isNullOrEmpty()){
                llCountry.visibility = View.GONE
            }
            else {
                tvCountry.text = currentTrackItem!!.country

            }
        }
    }
    companion object {
        var currentTrackItem: Track? = null
    }
}