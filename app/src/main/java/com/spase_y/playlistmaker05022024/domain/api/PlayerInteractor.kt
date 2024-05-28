package com.spase_y.playlistmaker05022024.domain.api

interface PlayerInteractor {
    fun mdPlayerStart()
    fun mdPlayerPause()
    fun mdPlayerRelease()
    fun getIsPause(): Boolean
}