package com.spase_y.playlistmaker05022024.search.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.playlistmaker05022024.databinding.FragmentFavouritesMedialibraryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.spase_y.playlistmaker05022024.search.ui.view_model.MedialibraryFavoritesViewModel

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

    companion object {
        fun newInstance(): MedialibraryFavoritesFragment {
            return MedialibraryFavoritesFragment()
        }
    }
}