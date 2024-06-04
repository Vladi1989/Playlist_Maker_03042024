package com.spase_y.playlistmaker05022024.player.domain.api

interface PlayerInteractor {
    fun mdPlayerStart()
    fun mdPlayerPause()
    fun mdPlayerRelease()
    fun getIsPause(): Boolean
    fun getDuration(): Long
    fun getCurrentPosition(): Int
    fun setOnCompleteListener(callback: () -> Unit)
}