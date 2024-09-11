package com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.create_playlist.ui.presentation.CreatePlaylistFragment
import com.spase_y.playlistmaker05022024.databinding.FragmentPlaylistsMedialibraryBinding
import com.spase_y.playlistmaker05022024.main.ui.MainActivity
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.view_model.MedialibraryPlaylistsViewModel
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.adapter.TracksPlaylistAdapter
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.model.MedialibraryPlaylistScreenState
import com.spase_y.playlistmaker05022024.playlist_screen.ui.presentation.PlaylistScreenFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedialibraryPlaylistsFragment : Fragment() {

    private val viewModel: MedialibraryPlaylistsViewModel by viewModel()
    private var _binding: FragmentPlaylistsMedialibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsMedialibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val adapter = TracksPlaylistAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updateButton.setOnClickListener{
            requireActivity().supportFragmentManager.
            beginTransaction().add(R.id.fragmentContainerView,
                CreatePlaylistFragment()).commit()
        }
        adapter.onPlaylistClick = {
            openPlaylistScreen(it)
        }
        binding.rvPlaylist.adapter = adapter
        viewModel.loadMyPlaylists()
        viewModel.getScreenStateLD().observe(viewLifecycleOwner){
            when(it){
                is MedialibraryPlaylistScreenState.Loading -> {
                    binding.ivNotFound.visibility = View.GONE
                    binding.tvNotFound.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is MedialibraryPlaylistScreenState.Empty -> {
                    binding.ivNotFound.visibility = View.VISIBLE
                    binding.tvNotFound.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }
                is MedialibraryPlaylistScreenState.Result -> {
                    binding.ivNotFound.visibility = View.GONE
                    binding.tvNotFound.visibility = View.GONE
                    binding.pbLoading.visibility = View.GONE
                    adapter.playlistItems = it.list
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).showBottomNavigation()
    }
    fun showPlaylistCreatedMessage() {
        binding.tvPlaylistCreated.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): MedialibraryPlaylistsFragment {
            return MedialibraryPlaylistsFragment()
        }
    }
    val gson = Gson()
    fun openPlaylistScreen(playlist: Playlist) {
        val fragment = PlaylistScreenFragment().apply {
            arguments = Bundle().apply {
                putString("playlist",gson.toJson(playlist))
            }
        }
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
    }
}