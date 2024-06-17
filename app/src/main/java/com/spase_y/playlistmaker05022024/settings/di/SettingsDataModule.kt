package com.spase_y.playlistmaker05022024.settings.di

import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsInteractor
import com.spase_y.playlistmaker05022024.settings.domain.impl.SettingsInteractorImpl
import com.spase_y.playlistmaker05022024.sharing.domain.api.SharingInteractor
import com.spase_y.playlistmaker05022024.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val settingsDataModule = module {
    single<SettingsInteractor> {
       SettingsInteractorImpl(get())
    }
    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
}