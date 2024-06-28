package com.spase_y.playlistmaker05022024.search.di

import com.spase_y.playlistmaker05022024.search.ui.view_model.MedialibraryFavoritesViewModel
import com.spase_y.playlistmaker05022024.search.ui.view_model.MedialibraryPlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val medialibraryModule = module {

    viewModel {
        MedialibraryFavoritesViewModel()
    }

    viewModel {
        MedialibraryPlaylistsViewModel()
    }
}