package com.spase_y.playlistmaker05022024.settings.di

import com.spase_y.playlistmaker05022024.settings.ui.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsViewModelModule = module {
    viewModel<SettingsViewModel> {
        SettingsViewModel(get(), get())
    }
}