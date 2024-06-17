package com.spase_y.playlistmaker05022024.player.data

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerRepository

class PlayerRepositoryImpl(context: Context, soundUrl: String): PlayerRepository {
    val mediaPlayer: MediaPlayer = MediaPlayer.create(context, Uri.parse(soundUrl))
    override fun mdPlayerStart() {
        mediaPlayer.start()
        isPause = false
    }
    override fun mdPlayerPause() {
        mediaPlayer.pause()
        isPause = true
    }

    override fun mdPlayerRelease() {
        mediaPlayer.release()
        isPause = true
    }

    override fun getDuration(): Long {
        return mediaPlayer.duration.toLong()
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun setOnCompleteListener(callback: () -> Unit) {
        mediaPlayer.setOnCompletionListener {
            callback.invoke()
        }
    }

    override var isPause: Boolean = true

}