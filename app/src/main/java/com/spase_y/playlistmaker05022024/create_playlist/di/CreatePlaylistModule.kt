package com.spase_y.playlistmaker05022024.create_playlist.di

import com.spase_y.playlistmaker05022024.create_playlist.ui.view_model.CreatePlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createPlaylistModule = module {
    viewModel{
        CreatePlaylistViewModel(get())
    }
}