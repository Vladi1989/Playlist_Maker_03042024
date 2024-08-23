package com.spase_y.playlistmaker05022024.player.ui.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.main.ui.MainActivity
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.ARTIST_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.ARTWORK_URL_100_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.COLLECTION_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.COUNTRY_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.PREWIEW_URL_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.PRIMARY_GENRE_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.RELEASE_DATE_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.TRACK_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.TRACK_TIME_MILLIS_TAG
import com.spase_y.playlistmaker05022024.player.ui.view_model.PlayerViewModel
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.utils.isDarkTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    private lateinit var tvCurrentTime: TextView
    private var isPlaybackCompleted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ScrollView>(R.id.root).setOnClickListener{

        }
        (requireActivity() as MainActivity).hideBottomNavigation()
        val buttonBack = view.findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            (requireActivity() as MainActivity).showBottomNavigation()

        }

        val trackName = arguments?.getString(TRACK_NAME_TAG).toString()
        val artistName = arguments?.getString(ARTIST_NAME_TAG).toString()
        val trackTimeMillis = arguments?.getLong(TRACK_TIME_MILLIS_TAG, 0L) ?: 0L
        val artworkUrl100 = arguments?.getString(ARTWORK_URL_100_TAG).toString()
        val collectionName = arguments?.getString(COLLECTION_NAME_TAG).toString()
        val releaseDate = arguments?.getString(RELEASE_DATE_TAG).toString()
        val primaryGenreName = arguments?.getString(PRIMARY_GENRE_NAME_TAG).toString()
        val country = arguments?.getString(COUNTRY_TAG).toString()

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
        val ibFavorite = view.findViewById<ImageButton>(R.id.ibFavorite)
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
            tvAlbomName.text = truncateText(currentTrackItem.collectionName, 20)
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
                startTimerJob()
            } else {
                ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
                viewModel.mdPlayerPause()
                pauseTimerJob()
            }
        }
        viewModel.getIsTrackSaved().observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    if(isDarkTheme(requireContext())){
                        ibFavorite.setBackgroundResource(R.drawable.favorite_night_on)
                    }
                    else {
                        ibFavorite.setBackgroundResource(R.drawable.favorite_day_on)
                    }
                }
                false -> {
                    if(isDarkTheme(requireContext())){
                        ibFavorite.setBackgroundResource(R.drawable.favorite_night_off)
                    }
                    else {
                        ibFavorite.setBackgroundResource(R.drawable.favorite_day_off)
                    }

                }

                null -> {}
            }
        }

        viewModel.checkIsTrackSaved(currentTrackItem)
        ibFavorite.setOnClickListener {
            if (viewModel.getIsTrackSaved().value == true) {
                viewModel.removeTrackFromFavorites(currentTrackItem)
            } else if (viewModel.getIsTrackSaved().value == false) {
                viewModel.addTrackToFavorites(currentTrackItem)
            }
        }
        viewModel.setOnCompleteListener {
            handlePlaybackCompletion()
        }

        tvCurrentTime.text = "00:00"
        Log.d("PlayerFragment", "onViewCreated: Setting initial tvCurrentTime to 00:00")
    }

    fun startTimerJob() {
        timerJob =
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    if (isPlaybackCompleted) {
                        break
                    }
                    val currentPosition =
                        viewModel.roundToNearestThousand(viewModel.getCurrentPosition()).toLong()
                    tvCurrentTime.text = viewModel.formatText(currentPosition)
                    Log.d(
                        "PlayerFragment",
                        "startTimerJob: Updating tvCurrentTime to ${
                            viewModel.formatText(currentPosition)
                        }"
                    )
                    delay(300)
                }
            }
        timerJob?.start()
    }

    fun pauseTimerJob() {
        timerJob?.cancel()
        timerJob = null
        Log.d("PlayerFragment", "pauseTimerJob: Timer job cancelled")
    }

    private fun handlePlaybackCompletion() {
        isPlaybackCompleted = true
        pauseTimerJob()
        tvCurrentTime.text = "00:00"
        Log.d("PlayerFragment", "Playback completed. Setting tvCurrentTime to 00:00")
        viewModel.mdPlayerPause()
        ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
    }

    private fun truncateText(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }

    private var timerJob: Job? = null

    override fun onPause() {
        super.onPause()
        pauseTimerJob()
        viewModel.mdPlayerPause()
        ibPlay.setBackgroundResource(R.drawable.baseline_play_circle_24)
        Log.d("PlayerFragment", "onPause: Pausing player and cancelling timer job")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.mdPlayerRelease()
        pauseTimerJob()
        Log.d("PlayerFragment", "onDestroyView: Releasing player and cancelling timer job")
    }
}