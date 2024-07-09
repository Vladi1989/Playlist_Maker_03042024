package com.spase_y.playlistmaker05022024.player.di

import android.media.MediaPlayer
import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.player.domain.impl.FormaterInteractorImpl
import com.spase_y.playlistmaker05022024.player.domain.impl.PlayerInteractorImpl
import org.koin.dsl.module

val playerDataModule = module {
    factory<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }
    single<FormaterInteractor> {
        FormaterInteractorImpl()
    }
    factory<MediaPlayer> {
        MediaPlayer()
    }
}