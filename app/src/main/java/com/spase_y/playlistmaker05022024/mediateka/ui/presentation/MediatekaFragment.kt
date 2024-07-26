package com.spase_y.playlistmaker05022024.mediateka.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.FragmentMediatekaBinding
import com.spase_y.playlistmaker05022024.mediateka.adapter.MedialibraryViewPageAdapter


class MediatekaFragment : Fragment() {
    private lateinit var binding: FragmentMediatekaBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediatekaBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewPager.adapter = MedialibraryViewPageAdapter(childFragmentManager, lifecycle)
        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.favorite_track)
                    1 -> tab.text = getString(R.string.playlist_track)
                }
            }
        tabLayoutMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator.detach()
    }
}