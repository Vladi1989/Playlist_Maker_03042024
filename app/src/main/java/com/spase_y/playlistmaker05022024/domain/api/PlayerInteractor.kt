package com.spase_y.playlistmaker05022024.domain.api

import android.media.MediaPlayer
import android.widget.ImageView
import com.bumptech.glide.RequestManager

interface PlayerInteractor {
    fun formatText(long: Long): String
    fun formatUrlImage(string: String): String
    fun formatYear(string: String): String
    fun roundToNearestThousand(milliseconds: Int): Int
    fun mdPlayerStart(mediaPlayer: MediaPlayer)
    fun mdPlayerPause(mediaPlayer: MediaPlayer)

    fun mdPlayerRelease(mediaPlayer: MediaPlayer)

    var isPause: Boolean

}
