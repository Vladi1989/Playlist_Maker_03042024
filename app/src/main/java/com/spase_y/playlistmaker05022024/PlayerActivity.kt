package com.spase_y.playlistmaker05022024

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var mdPlayer: MediaPlayer
    private lateinit var ibPlay: ImageButton
    val trackDuration by lazy {
        mdPlayer.duration
    }
    val handler = Handler(Looper.getMainLooper()!!)
    var isPause = 0
    val tvCurrentTime by lazy {
        findViewById<TextView>(R.id.tvCurrentTime)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
        val trackName = intent.getStringExtra("trackName").toString()
        val previewUrl = intent.getStringExtra("previewUrl").toString()
        val artistName = intent.getStringExtra("artistName").toString()
        val trackTimeMillis = intent.getLongExtra("trackTimeMillis",0L)
        val artworkUrl100 = intent.getStringExtra("artworkUrl100").toString()
        val collectionName = intent.getStringExtra("collectionName").toString()
        val releaseDate = intent.getStringExtra("releaseDate").toString()
        val primaryGenreName = intent.getStringExtra("primaryGenreName").toString()
        val country = intent.getStringExtra("country").toString()

        val currentTrackItem = Track(previewUrl,trackName,artistName,trackTimeMillis,artworkUrl100,collectionName,
        releaseDate,primaryGenreName,country)
        ibPlay = findViewById<ImageButton>(R.id.ibPlay)

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
        mdPlayer = MediaPlayer.create(this, Uri.parse(previewUrl))
        tvName.text = currentTrackItem.trackName
        tvArtist.text = currentTrackItem.artistName
        tvDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentTrackItem!!.trackTimeMillis)
        val big = currentTrackItem.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
        Glide.with(this)
            .load(big)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(ivIcon)

        if (currentTrackItem.releaseDate.isNullOrEmpty()){
            llYear.visibility = View.GONE
        }
        else{
            tvYear.text = currentTrackItem.releaseDate.replaceAfter("-","").replace("-","")
        }
        if (currentTrackItem.collectionName.isNullOrEmpty()){
           llAlbom.visibility = View.GONE
        }
        else{
            tvAlbomName.text = currentTrackItem.collectionName
        }
        if(currentTrackItem.primaryGenreName.isNullOrEmpty()) {
            llGenre.visibility = View.GONE
        }
        else {
            tvGenre.text = currentTrackItem.primaryGenreName
        }
        if (currentTrackItem.country.isNullOrEmpty()){
            llCountry.visibility = View.GONE
        }
        else {
            tvCountry.text = currentTrackItem.country

        }
        ibPlay.setOnClickListener {
            if(isPause == 0){
                mdPlayer.start()
                ibPlay.setBackgroundResource(R.drawable.pause)
                isPause = 1
                handler.post(timerRunneble)
            }
            else {
                mdPlayer.pause()
                ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
                isPause = 0
                handler.removeCallbacksAndMessages(null)
            }
        }
        mdPlayer.setOnCompletionListener {
            mdPlayer.pause()
            isPause = 0
            ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
        }
        tvCurrentTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackDuration)
    }
    val timerRunneble = object: Runnable {

        override fun run() {
            val currentPosition = roundToNearestThousand(mdPlayer.currentPosition)
            Log.d("TAG",currentPosition.toString())
            tvCurrentTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentPosition)
            handler.postDelayed(this,100)
        }

    }
    fun roundToNearestThousand(milliseconds: Int): Int {
        val remainder = milliseconds % 1000
        return if (remainder < 500) {
            milliseconds - remainder
        } else {
            milliseconds + (1000 - remainder)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        mdPlayer.pause()
        isPause = 0
        ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
    }

    override fun onDestroy() {
        super.onDestroy()
        mdPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}