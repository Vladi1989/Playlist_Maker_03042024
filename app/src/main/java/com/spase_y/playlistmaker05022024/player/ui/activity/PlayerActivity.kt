package com.spase_y.playlistmaker05022024.player.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.player.ui.view_model.PlayerViewModel
import com.spase_y.playlistmaker05022024.search.domain.model.Track

class PlayerActivity : AppCompatActivity() {
    private lateinit var viewModel: PlayerViewModel
    private lateinit var ibPlay: ImageButton
    val handler = Handler(Looper.getMainLooper()!!)
    val tvCurrentTime by lazy {
        findViewById<TextView>(R.id.tvCurrentTime)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val previewUrl = intent.getStringExtra("previewUrl").toString()
        viewModel = ViewModelProvider(this, PlayerViewModel.getViewModelFactory(previewUrl))[PlayerViewModel::class.java]
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
        val trackName = intent.getStringExtra("trackName").toString()
        val artistName = intent.getStringExtra("artistName").toString()
        val trackTimeMillis = intent.getLongExtra("trackTimeMillis", 0L)
        val artworkUrl100 = intent.getStringExtra("artworkUrl100").toString()
        val collectionName = intent.getStringExtra("collectionName").toString()
        val releaseDate = intent.getStringExtra("releaseDate").toString()
        val primaryGenreName = intent.getStringExtra("primaryGenreName").toString()
        val country = intent.getStringExtra("country").toString()
        val currentTrackItem = Track(previewUrl,trackName,artistName,trackTimeMillis,artworkUrl100,collectionName,
        releaseDate,primaryGenreName,country)
        ibPlay = findViewById(R.id.ibPlay)
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
        tvName.text = currentTrackItem.trackName
        tvArtist.text = currentTrackItem.artistName
        tvDuration.text = viewModel.formatText(currentTrackItem.trackTimeMillis)
        val big = viewModel.formatUrlImage(currentTrackItem.artworkUrl100)
        Glide.with(this)
            .load(big)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(ivIcon)
        if (currentTrackItem.releaseDate.isNullOrEmpty()) {
            llYear.visibility = View.GONE
        } else {
            tvYear.text = viewModel.formatYear(currentTrackItem.releaseDate)
        }
        if (currentTrackItem.collectionName.isNullOrEmpty()) {
            llAlbom.visibility = View.GONE
        } else {
            tvAlbomName.text = currentTrackItem.collectionName
        }
        if (currentTrackItem.primaryGenreName.isNullOrEmpty()) {
            llGenre.visibility = View.GONE
        } else {
            tvGenre.text = currentTrackItem.primaryGenreName
        }
        if (currentTrackItem.country.isNullOrEmpty()) {
            llCountry.visibility = View.GONE
        } else {
            tvCountry.text = currentTrackItem.country

        }
        ibPlay.setOnClickListener {
            if (viewModel.getIsPause()) {
                viewModel.mdPlayerStart()
                ibPlay.setBackgroundResource(R.drawable.pause)
                handler.post(timerRunneble)
            } else {
                ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
                viewModel.mdPlayerPause()
                handler.removeCallbacksAndMessages(null)
            }
        }
        viewModel.setOnCompleteListener{
            viewModel.mdPlayerPause()
            ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
        }
        tvCurrentTime.text = "00:00"
    }
    val timerRunneble = object : Runnable {

        override fun run() {
            val currentPosition =
                viewModel.roundToNearestThousand(viewModel.getCurrentPosition()).toLong()
            tvCurrentTime.text = viewModel.formatText(currentPosition)
            handler.postDelayed(this, 100)
        }
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        viewModel.mdPlayerPause()
        ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.mdPlayerRelease()
        handler.removeCallbacksAndMessages(null)
    }
}