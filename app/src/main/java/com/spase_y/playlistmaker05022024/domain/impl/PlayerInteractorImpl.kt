package com.spase_y.playlistmaker05022024.domain.impl

import android.media.MediaPlayer
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.domain.api.PlayerInteractor
import java.text.SimpleDateFormat
import java.util.*

class PlayerInteractorImpl: PlayerInteractor {

    override fun formatText(long: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(long)
    }
    override fun formatUrlImage(string: String): String {
        return string.replaceAfterLast('/',"512x512bb.jpg")
    }

    override fun formatYear(string: String): String {
        return string.replaceAfter("-","").replace("-","")
    }

    override fun roundToNearestThousand(milliseconds: Int): Int {
        val remainder = milliseconds % 1000
        return if (remainder < 500) {
            milliseconds - remainder
        } else {
            milliseconds + (1000 - remainder)
        }
    }

    override fun mdPlayerStart(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
        isPause = false
    }

    override fun mdPlayerPause(mediaPlayer: MediaPlayer) {
        mediaPlayer.pause()
        isPause = true
    }

    override fun mdPlayerRelease(mediaPlayer: MediaPlayer) {
        mediaPlayer.release()
        isPause = true
    }

    override var isPause: Boolean = true

}