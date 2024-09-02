package com.spase_y.playlistmaker05022024.mediateka.favorites.di

import com.spase_y.playlistmaker05022024.mediateka.favorites.data.impl.DataBaseRepositoryImpl
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseInteractor
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseRepository
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.impl.DataBaseInteractorImpl
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.view_model.MedialibraryFavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        MedialibraryFavoritesViewModel(get())
    }
    single<DataBaseInteractor> {
        DataBaseInteractorImpl(get())
    }
    single<DataBaseRepository> {
        DataBaseRepositoryImpl(get())
    }
}