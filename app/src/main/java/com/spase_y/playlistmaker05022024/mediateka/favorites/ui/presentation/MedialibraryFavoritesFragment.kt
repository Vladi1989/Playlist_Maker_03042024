package com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.FragmentFavouritesMedialibraryBinding
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.view_model.MedialibraryFavoritesViewModel
import com.spase_y.playlistmaker05022024.player.ui.presentation.PlayerFragment
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.search.ui.adapter.TracksAdapter
import com.spase_y.playlistmaker05022024.utils.BottomSpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedialibraryFavoritesFragment : Fragment() {

    private val viewModel: MedialibraryFavoritesViewModel by viewModel()
    private var _binding: FragmentFavouritesMedialibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesMedialibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    val tracksAdapter = TracksAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.addItemDecoration(BottomSpaceItemDecoration(180))
        viewModel.getFavoritesTracks().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.rvList.visibility = View.GONE
                binding.clNotFound.visibility = View.VISIBLE
            } else {
                binding.rvList.visibility = View.VISIBLE
                binding.clNotFound.visibility = View.GONE
                tracksAdapter.listTracks = ArrayList(it)
                tracksAdapter.onItemClick = {
                    showPlayerFragment(it)
                }
                binding.rvList.adapter = tracksAdapter
                binding.rvList.layoutManager = LinearLayoutManager(requireContext())

            }
        }

    }

    private fun showPlayerFragment(track: Track) {
        if (viewModel.clickDebounce()) {
            val playerFragment = PlayerFragment()
            val args = Bundle()
            args.putString(TRACK_NAME_TAG, track.trackName)
            args.putString(PREWIEW_URL_TAG, track.previewUrl)
            args.putString(ARTIST_NAME_TAG, track.artistName)
            args.putLong(TRACK_TIME_MILLIS_TAG, track.trackTimeMillis)
            args.putString(ARTWORK_URL_100_TAG, track.artworkUrl100)
            args.putString(COLLECTION_NAME_TAG, track.collectionName)
            args.putString(RELEASE_DATE_TAG, track.releaseDate)
            args.putString(PRIMARY_GENRE_NAME_TAG, track.primaryGenreName)
            args.putString(COUNTRY_TAG, track.country)
            playerFragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, playerFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): MedialibraryFavoritesFragment {
            return MedialibraryFavoritesFragment()
        }
    }
}

const val TRACK_NAME_TAG = "trackName"
const val PREWIEW_URL_TAG = "previewUrl"
const val ARTIST_NAME_TAG = "artistName"
const val TRACK_TIME_MILLIS_TAG = "trackTimeMillis"
const val ARTWORK_URL_100_TAG = "artworkUrl100"
const val COLLECTION_NAME_TAG = "collectionName"
const val RELEASE_DATE_TAG = "releaseDate"
const val PRIMARY_GENRE_NAME_TAG = "primaryGenreName"
const val COUNTRY_TAG = "country"