package com.spase_y.playlistmaker05022024.utils

import android.content.Context
import com.spase_y.playlistmaker05022024.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.domain.api.PlayerRepository
import com.spase_y.playlistmaker05022024.domain.impl.FormaterInteractorImpl
import com.spase_y.playlistmaker05022024.data.impl.PlayerRepositoryImpl
import com.spase_y.playlistmaker05022024.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.domain.impl.PlayerInteractorImpl

object Creator {
    private fun getPlayerRepositoryImpl(context: Context, soundUrl: String): PlayerRepository{
        return PlayerRepositoryImpl(context,soundUrl)
    }
    fun providePlayerInteractor(context: Context, soundUrl: String): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepositoryImpl(context, soundUrl))
    }


    fun getFormaterInteractorImpl(): FormaterInteractor {
        return FormaterInteractorImpl()
    }
}