package com.spase_y.playlistmaker05022024.player.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.spase_y.playlistmaker05022024.creator.Creator
import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.utils.App

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val formaterInteractor: FormaterInteractor
):ViewModel() {
    fun formatText(trackTimeMillis: Long): String {
        return formaterInteractor.formatText(trackTimeMillis)
    }
    fun formatUrlImage(artworkUrl100: String): String {
        return formaterInteractor.formatUrlImage(artworkUrl100)
    }

    fun formatYear(releaseDate: String): String {
        return formaterInteractor.formatYear(releaseDate)
    }

    fun getIsPause(): Boolean {
        return playerInteractor.getIsPause()
    }

    fun mdPlayerStart() {
        playerInteractor.mdPlayerStart()
    }

    fun mdPlayerPause() {
        playerInteractor.mdPlayerPause()
    }

    fun roundToNearestThousand(currentPosition: Int): Int {
        return formaterInteractor.roundToNearestThousand(currentPosition)
    }

    fun mdPlayerRelease() {
        playerInteractor.mdPlayerRelease()
    }

    fun getDuration(): Long {
        return playerInteractor.getDuration()
    }

    fun getCurrentPosition(): Int {
        return playerInteractor.getCurrentPosition()

    }
    fun setOnCompleteListener(function: () -> Unit) {
        playerInteractor.setOnCompleteListener(function)
    }
    companion object {
        fun getViewModelFactory(soundUrl:String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App
                PlayerViewModel(Creator.providePlayerInteractor(app,soundUrl),Creator.provideFormaterInteractor())
            }
        }
    }
}