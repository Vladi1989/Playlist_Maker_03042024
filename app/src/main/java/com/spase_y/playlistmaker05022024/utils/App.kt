package com.spase_y.playlistmaker05022024.utils

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.spase_y.playlistmaker05022024.player.di.playerDataModule
import com.spase_y.playlistmaker05022024.player.di.playerRepositoryModule
import com.spase_y.playlistmaker05022024.player.di.playerViewModelModule
import com.spase_y.playlistmaker05022024.search.di.medialibraryModule
import com.spase_y.playlistmaker05022024.search.di.searchDataModule
import com.spase_y.playlistmaker05022024.search.di.searchRepositoryModule
import com.spase_y.playlistmaker05022024.search.di.searchViewModelModule
import com.spase_y.playlistmaker05022024.settings.di.settingsDataModule
import com.spase_y.playlistmaker05022024.settings.di.settingsRepositoryModule
import com.spase_y.playlistmaker05022024.settings.di.settingsViewModelModule
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsInteractor
import com.spase_y.playlistmaker05022024.sharing.di.externalNavigatorModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    val settingsInteractor: SettingsInteractor by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(externalNavigatorModule,
                settingsRepositoryModule,
                settingsViewModelModule,
                settingsDataModule,
                searchDataModule,
                searchRepositoryModule,
                searchViewModelModule,
                playerDataModule,
                playerRepositoryModule,
                playerViewModelModule,
                medialibraryModule
            )
        }
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
const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"