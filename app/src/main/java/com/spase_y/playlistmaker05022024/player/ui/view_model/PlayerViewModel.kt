package com.spase_y.playlistmaker05022024.player.ui.view_model


import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spase_y.playlistmaker05022024.player.data.PlayerRepositoryImpl
import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.player.domain.impl.FormaterInteractorImpl
import com.spase_y.playlistmaker05022024.player.domain.impl.PlayerInteractorImpl

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val formaterInteractor: FormaterInteractor,
    private val url: String
) : ViewModel() {
    init {
        playerInteractor.provideUrl(url)
    }

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
}

