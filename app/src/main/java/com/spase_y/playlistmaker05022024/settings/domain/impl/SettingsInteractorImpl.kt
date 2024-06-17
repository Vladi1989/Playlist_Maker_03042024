package com.spase_y.playlistmaker05022024.settings.domain.impl

import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsInteractor
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsRepository
import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository):
    SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return settingsRepository.getThemeSettings()
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        return settingsRepository.updateThemeSetting(settings)
    }

}