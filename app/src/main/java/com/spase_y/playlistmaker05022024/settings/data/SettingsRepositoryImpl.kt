package com.spase_y.playlistmaker05022024.settings.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsRepository
import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings

class SettingsRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    SettingsRepository {
    private val gson = Gson()
    override fun getThemeSettings(): ThemeSettings {
        val data = sharedPreferences.getString(THEME_SETTING_KEY, "")
        if (data == "") {
            return ThemeSettings(false)
        } else {
            return gson.fromJson(data, ThemeSettings::class.java)
        }
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        val data = gson.toJson(settings)
        sharedPreferences.edit().putString(THEME_SETTING_KEY, data).apply()
    }

    private companion object {
        val THEME_SETTING_KEY = "THEME_SETTING_KEY"
    }
}


