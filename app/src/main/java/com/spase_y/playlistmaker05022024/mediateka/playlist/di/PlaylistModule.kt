package com.spase_y.playlistmaker05022024.mediateka.playlist.di

import com.spase_y.playlistmaker05022024.mediateka.playlist.data.impl.PlaylistDatabaseRepositoryImpl
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistRepository
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.impl.PlaylistInteractorImpl
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.view_model.MedialibraryPlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlistModule = module {

    viewModel {
        MedialibraryPlaylistsViewModel(get())
    }
    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }
    single<PlaylistRepository> {
        PlaylistDatabaseRepositoryImpl(get())
    }
}