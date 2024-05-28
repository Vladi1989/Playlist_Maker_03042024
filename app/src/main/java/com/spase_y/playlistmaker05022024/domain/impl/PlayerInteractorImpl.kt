package com.spase_y.playlistmaker05022024.domain.impl

import com.spase_y.playlistmaker05022024.data.impl.PlayerRepositoryImpl
import com.spase_y.playlistmaker05022024.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.domain.api.PlayerRepository

class PlayerInteractorImpl(val repository: PlayerRepository): PlayerInteractor {
    override fun mdPlayerStart() {
        repository.mdPlayerStart()
    }

    override fun mdPlayerPause() {
        repository.mdPlayerPause()
    }

    override fun mdPlayerRelease() {
        repository.mdPlayerRelease()
    }

    override fun getIsPause(): Boolean {
        return repository.isPause
    }

}