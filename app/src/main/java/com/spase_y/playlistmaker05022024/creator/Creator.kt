package com.spase_y.playlistmaker05022024.creator

import android.content.Context
import android.content.SharedPreferences
import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerRepository
import com.spase_y.playlistmaker05022024.player.domain.impl.FormaterInteractorImpl
import com.spase_y.playlistmaker05022024.player.data.PlayerRepositoryImpl
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.player.domain.impl.PlayerInteractorImpl
import com.spase_y.playlistmaker05022024.search.data.NetworkClient
import com.spase_y.playlistmaker05022024.search.data.SearchRepositoryImpl
import com.spase_y.playlistmaker05022024.search.data.db.LocalStorage
import com.spase_y.playlistmaker05022024.search.data.network.RetrofitNetworkClient
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsInteractor
import com.spase_y.playlistmaker05022024.settings.domain.api.SettingsRepository
import com.spase_y.playlistmaker05022024.sharing.domain.api.SharingInteractor
import com.spase_y.playlistmaker05022024.sharing.domain.impl.SharingInteractorImpl
import com.spase_y.playlistmaker05022024.search.domain.api.SearchInteractor
import com.spase_y.playlistmaker05022024.search.domain.api.SearchRepository
import com.spase_y.playlistmaker05022024.search.domain.impl.SearchInteractorImpl
import com.spase_y.playlistmaker05022024.settings.data.SettingsRepositoryImpl
import com.spase_y.playlistmaker05022024.settings.domain.impl.SettingsInteractorImpl
import com.spase_y.playlistmaker05022024.sharing.data.ExternalNavigatorImpl
import com.spase_y.playlistmaker05022024.sharing.domain.api.ExternalNavigator

object Creator {
    private fun getPlayerRepository(context: Context, soundUrl: String): PlayerRepository {
        return PlayerRepositoryImpl(context,soundUrl)
    }
    fun providePlayerInteractor(context: Context, soundUrl: String): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository(context, soundUrl))
    }


    fun provideFormaterInteractor(): FormaterInteractor {
        return FormaterInteractorImpl()
    }

    fun provideSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImpl(provideExternalNavigator(context))
    }
    private fun provideExternalNavigator(context: Context):ExternalNavigator{
        return ExternalNavigatorImpl(context)
    }

    fun provideSettingsInteractor(context: Context): SettingsInteractor {
        return SettingsInteractorImpl(provideSettingRepository(context))
    }
    private fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY,Context.MODE_PRIVATE)
    }
    private fun provideSettingRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(provideSharedPreferences(context))
    }

    fun provideSearchInteractor(context: Context): SearchInteractor {
        return SearchInteractorImpl(provideSearchRepository(context))
    }

    private fun provideSearchRepository(context: Context): SearchRepository {
        return SearchRepositoryImpl(provideLocalStorage(context),provideNetworkClient(context))
    }

    private fun provideNetworkClient(context: Context): NetworkClient {
        return RetrofitNetworkClient(context)
    }

    private fun provideLocalStorage(context: Context): LocalStorage {
        return LocalStorage(provideSharedPreferences(context))
    }

    private const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
}