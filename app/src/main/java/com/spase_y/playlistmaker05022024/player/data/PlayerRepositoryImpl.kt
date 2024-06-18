package com.spase_y.playlistmaker05022024.player.data

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerRepository

class PlayerRepositoryImpl(val mediaPlayer: MediaPlayer): PlayerRepository {
    override fun mdPlayerStart() {
        mediaPlayer?.start()
        isPause = false
    }
    override fun mdPlayerPause() {
        mediaPlayer?.pause()
        isPause = true
    }

    override fun mdPlayerRelease() {
        mediaPlayer?.release()
        isPause = true
    }

    override fun getDuration(): Long {
        return if (mediaPlayer == null) 0 else mediaPlayer!!.duration.toLong()
    }

    override fun getCurrentPosition(): Int {
        return if (mediaPlayer == null) 0 else mediaPlayer!!.currentPosition
    }

    override fun setOnCompleteListener(callback: () -> Unit) {
        mediaPlayer?.setOnCompletionListener {
            callback.invoke()
        }
    }

    override fun provideUrl(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
    }

    override var isPause: Boolean = true

}