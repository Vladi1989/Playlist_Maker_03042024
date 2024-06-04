package com.spase_y.playlistmaker05022024.settings.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.spase_y.playlistmaker05022024.creator.Creator
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsInteractor
import com.spase_y.playlistmaker05022024.sharing.domain.api.SharingInteractor
import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings
import com.spase_y.playlistmaker05022024.utils.App

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
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App
                SettingsViewModel(Creator.provideSharingInteractor(app),Creator.provideSettingsInteractor(app))
            }
        }
    }
}