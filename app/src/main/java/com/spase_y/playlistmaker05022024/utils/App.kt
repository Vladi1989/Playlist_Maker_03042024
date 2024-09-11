package com.spase_y.playlistmaker05022024.utils

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.spase_y.playlistmaker05022024.create_playlist.di.createPlaylistModule
import com.spase_y.playlistmaker05022024.mediateka.root.di.appDatabaseModule
import com.spase_y.playlistmaker05022024.mediateka.favorites.di.favoritesModule
import com.spase_y.playlistmaker05022024.player.di.playerDataModule
import com.spase_y.playlistmaker05022024.player.di.playerRepositoryModule
import com.spase_y.playlistmaker05022024.player.di.playerViewModelModule
import com.spase_y.playlistmaker05022024.mediateka.playlist.di.playlistModule
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
            modules(
                externalNavigatorModule,
                settingsRepositoryModule,
                settingsViewModelModule,
                settingsDataModule,
                searchDataModule,
                searchRepositoryModule,
                searchViewModelModule,
                playerDataModule,
                playerRepositoryModule,
                playerViewModelModule,
                playlistModule,
                favoritesModule,
                appDatabaseModule,
                createPlaylistModule
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
const val CLICK_DEBOUNCE_DELAY = 2000L
const val SEARCH_DEBOUNCE_DELAY = 2000L


fun isDarkTheme(context: Context): Boolean {
    val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}