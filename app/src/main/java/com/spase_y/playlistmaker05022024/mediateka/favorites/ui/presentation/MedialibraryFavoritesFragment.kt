package com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.FragmentFavouritesMedialibraryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.view_model.MedialibraryFavoritesViewModel
import com.spase_y.playlistmaker05022024.player.ui.presentation.PlayerFragment
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.search.ui.adapter.TracksAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoritesTracks().observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.rvList.visibility = View.GONE
                binding.clNotFound.visibility = View.VISIBLE
            }
            else {
                binding.rvList.visibility = View.VISIBLE
                binding.clNotFound.visibility = View.GONE
                val tracksAdapter = TracksAdapter()
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
            args.putString("trackName", track.trackName)
            args.putString("previewUrl", track.previewUrl)
            args.putString("artistName", track.artistName)
            args.putLong("trackTimeMillis", track.trackTimeMillis)
            args.putString("artworkUrl100", track.artworkUrl100)
            args.putString("collectionName", track.collectionName)
            args.putString("releaseDate", track.releaseDate)
            args.putString("primaryGenreName", track.primaryGenreName)
            args.putString("country", track.country)
            playerFragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, playerFragment)
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