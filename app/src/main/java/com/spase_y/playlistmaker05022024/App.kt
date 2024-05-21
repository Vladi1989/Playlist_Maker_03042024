package com.spase_y.playlistmaker05022024

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    var darkTheme = false
    private val sharedPreferences by lazy {
        getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        darkTheme = sharedPreferences.getBoolean(DARK_THEME_TAG, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        sharedPreferences.edit().putBoolean(DARK_THEME_TAG, darkThemeEnabled).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        const val PREFS_TAG = "Prefs"
        const val DARK_THEME_TAG = "Dark Theme"
    }
}