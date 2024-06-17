package com.spase_y.playlistmaker05022024.player.di

import com.spase_y.playlistmaker05022024.player.data.PlayerRepositoryImpl
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val playerRepositoryModule = module {
    single<PlayerRepository>{
        PlayerRepositoryImpl(androidContext())
    }
}