package com.spase_y.playlistmaker05022024.settings.ui.view_model

import androidx.lifecycle.ViewModel
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsInteractor
import com.spase_y.playlistmaker05022024.sharing.domain.api.SharingInteractor
import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    fun getThemeSettings(): ThemeSettings{
        return settingsInteractor.getThemeSettings()
    }
    fun updateThemeSetting(settings: ThemeSettings) {
        return settingsInteractor.updateThemeSetting(settings)
    }
    fun shareApp(){
        sharingInteractor.shareApp()
    }
    fun openTerms(){
        sharingInteractor.openTerms()
    }
    fun openSupport(){
        sharingInteractor.openSupport()
    }
}