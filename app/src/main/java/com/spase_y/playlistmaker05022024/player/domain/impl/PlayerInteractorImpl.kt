package com.spase_y.playlistmaker05022024.player.domain.impl

import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerRepository

class PlayerInteractorImpl(val repository: PlayerRepository) : PlayerInteractor {
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

    override fun getDuration(): Long {
        return repository.getDuration()
    }

    override fun getCurrentPosition(): Int {
        return repository.getCurrentPosition()
    }

    override fun setOnCompleteListener(callback: () -> Unit) {
        repository.setOnCompleteListener(callback)
    }

    override fun provideUrl(url: String) {
        repository.provideUrl(url)
    }

}