package com.spase_y.playlistmaker05022024.settings.domain.api

import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}