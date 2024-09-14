package com.spase_y.playlistmaker05022024.playlist_screen.di

import com.spase_y.playlistmaker05022024.playlist_screen.ui.view_model.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlistScreenModule = module {
    viewModel{
        PlaylistsViewModel(get())
    }
}