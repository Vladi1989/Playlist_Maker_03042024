package com.spase_y.playlistmaker05022024.domain.api

interface PlayerRepository {
    fun mdPlayerStart()
    fun mdPlayerPause()
    fun mdPlayerRelease()

    var isPause: Boolean

}
