package com.spase_y.playlistmaker05022024.search.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spase_y.playlistmaker05022024.search.ui.fragment.MedialibraryFavoritesFragment
import com.spase_y.playlistmaker05022024.search.ui.fragment.MedialibraryPlaylistsFragment

class MedialibraryViewPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) MedialibraryFavoritesFragment.newInstance() else MedialibraryPlaylistsFragment.newInstance()
    }
}