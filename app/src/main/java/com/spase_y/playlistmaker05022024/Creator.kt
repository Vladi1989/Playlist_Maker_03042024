package com.spase_y.playlistmaker05022024

import com.spase_y.playlistmaker05022024.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.domain.impl.PlayerInteractorImpl

object Creator {
    fun getPlayerInteractorImpl(): PlayerInteractor{
        return PlayerInteractorImpl()
    }
}