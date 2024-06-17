package com.spase_y.playlistmaker05022024.settings.di

import android.content.Context
import android.content.SharedPreferences
import com.spase_y.playlistmaker05022024.settings.data.SettingsRepositoryImpl
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsRepository
import com.spase_y.playlistmaker05022024.utils.SHARED_PREFERENCES_KEY
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsRepositoryModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }
}