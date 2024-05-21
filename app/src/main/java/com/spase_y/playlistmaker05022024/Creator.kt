package com.spase_y.playlistmaker05022024

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.spase_y.playlistmaker05022024.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.domain.api.PlayerRepository
import com.spase_y.playlistmaker05022024.domain.impl.FormaterInteractorImpl
import com.spase_y.playlistmaker05022024.presentation.impl.PlayerRepositoryImpl

object Creator {
    fun getPlayerRepositoryImpl(context: Context, soundUrl: String): PlayerRepository{
        return PlayerRepositoryImpl(getMediaPlayer(context,soundUrl))
    }
    private fun getMediaPlayer(context: Context, soundUrl: String): MediaPlayer{
        return MediaPlayer.create(context, Uri.parse(soundUrl))
    }

    fun getFormaterInteractorImpl(): FormaterInteractor {
        return FormaterInteractorImpl()
    }
}