package com.spase_y.playlistmaker05022024

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    var darkTheme = false
    private val sharedPreferences by lazy {
        getSharedPreferences("Prefs", Context.MODE_PRIVATE)
    }
    override fun onCreate() {
        super.onCreate()
        darkTheme = sharedPreferences.getBoolean("Dark Theme",false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        sharedPreferences.edit().putBoolean("Dark Theme", darkThemeEnabled).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}