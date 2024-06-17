package com.spase_y.playlistmaker05022024.utils

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.spase_y.playlistmaker05022024.creator.Creator

class App : Application() {
    val settingsInteractor by lazy {Creator.provideSettingsInteractor(this)
        }
    override fun onCreate() {
        super.onCreate()
        switchTheme(settingsInteractor.getThemeSettings().isDarkTheme)
    }
    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}