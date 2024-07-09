package com.spase_y.playlistmaker05022024.player.ui.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.player.ui.view_model.PlayerViewModel
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayerFragment : Fragment() {

    private val previewUrl by lazy {
        arguments?.getString("previewUrl").toString()
    }
    private val viewModel: PlayerViewModel by viewModel {
        parametersOf(previewUrl)
    }
    private lateinit var ibPlay: ImageButton
    private val handler = Handler(Looper.getMainLooper()!!)
    private lateinit var tvCurrentTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonBack = view.findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val trackName = arguments?.getString("trackName").toString()
        val artistName = arguments?.getString("artistName").toString()
        val trackTimeMillis = arguments?.getLong("trackTimeMillis", 0L) ?: 0L
        val artworkUrl100 = arguments?.getString("artworkUrl100").toString()
        val collectionName = arguments?.getString("collectionName").toString()
        val releaseDate = arguments?.getString("releaseDate").toString()
        val primaryGenreName = arguments?.getString("primaryGenreName").toString()
        val country = arguments?.getString("country").toString()

        val currentTrackItem = Track(
            previewUrl, trackName, artistName, trackTimeMillis, artworkUrl100, collectionName,
            releaseDate, primaryGenreName, country
        )

        ibPlay = view.findViewById(R.id.ibPlay)
        val ivIcon = view.findViewById<ImageView>(R.id.ivIcon)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvArtist = view.findViewById<TextView>(R.id.tvArtistName)
        val tvDuration = view.findViewById<TextView>(R.id.tvDuration)
        val tvYear = view.findViewById<TextView>(R.id.tvYear)
        val tvGenre = view.findViewById<TextView>(R.id.tvGenre)
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        val tvAlbomName = view.findViewById<TextView>(R.id.tvAlbomName)
        val llAlbom = view.findViewById<LinearLayout>(R.id.llAlbom)
        val llYear = view.findViewById<LinearLayout>(R.id.llYear)
        val llGenre = view.findViewById<LinearLayout>(R.id.llGenre)
        val llCountry = view.findViewById<LinearLayout>(R.id.llCountry)
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime)

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
                handler.post(timerRunnable)
            } else {
                ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
                viewModel.mdPlayerPause()
                handler.removeCallbacksAndMessages(null)
            }
        }
        viewModel.setOnCompleteListener{
            handler.removeCallbacks(timerRunnable)
            tvCurrentTime.text = "00:00"
            viewModel.mdPlayerPause()
            ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)

        }
        tvCurrentTime.text = "00:00"
    }

    private val timerRunnable = object : Runnable {
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.mdPlayerRelease()
        handler.removeCallbacksAndMessages(null)
    }
}