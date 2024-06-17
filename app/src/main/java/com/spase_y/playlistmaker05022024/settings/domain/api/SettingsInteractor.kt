package com.spase_y.playlistmaker05022024.settings.domain.api

import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}