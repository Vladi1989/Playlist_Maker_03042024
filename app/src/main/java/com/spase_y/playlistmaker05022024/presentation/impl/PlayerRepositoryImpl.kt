package com.spase_y.playlistmaker05022024.presentation.impl

import android.media.MediaPlayer
import com.spase_y.playlistmaker05022024.domain.api.PlayerRepository

class PlayerRepositoryImpl(val mediaPlayer: MediaPlayer,): PlayerRepository {
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

    override var isPause: Boolean = true

}