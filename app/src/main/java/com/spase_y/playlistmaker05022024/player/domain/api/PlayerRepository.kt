package com.spase_y.playlistmaker05022024.player.domain.api

interface PlayerRepository {
    fun mdPlayerStart()
    fun mdPlayerPause()
    fun mdPlayerRelease()
    fun getDuration(): Long
    fun getCurrentPosition(): Int
    fun setOnCompleteListener(callback: () -> Unit)

    var isPause: Boolean

}
