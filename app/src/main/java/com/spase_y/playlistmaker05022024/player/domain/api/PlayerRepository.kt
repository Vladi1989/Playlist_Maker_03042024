package com.spase_y.playlistmaker05022024.player.domain.api

import java.net.URL

interface PlayerRepository {
    fun mdPlayerStart()
    fun mdPlayerPause()
    fun mdPlayerRelease()
    fun getDuration(): Long
    fun getCurrentPosition(): Int
    fun setOnCompleteListener(callback: () -> Unit)
    fun provideUrl(url: String)


    var isPause: Boolean

}
