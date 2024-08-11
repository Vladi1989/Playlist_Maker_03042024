package com.spase_y.playlistmaker05022024.mediateka.playlist.di

import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.view_model.MedialibraryPlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlistModule = module {

    viewModel {
        MedialibraryPlaylistsViewModel()
    }
}