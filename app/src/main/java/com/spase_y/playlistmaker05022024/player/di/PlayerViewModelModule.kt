package com.spase_y.playlistmaker05022024.player.di

import com.spase_y.playlistmaker05022024.player.ui.view_model.PlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playerViewModelModule = module {
    viewModel{(url: String)->
        PlayerViewModel(get(),get(),url)
    }
}