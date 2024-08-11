package com.spase_y.playlistmaker05022024.player.ui.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val formaterInteractor: FormaterInteractor,
    private val dataBaseInteractor: DataBaseInteractor,
    private val url: String
) : ViewModel() {
    private val isTrackSaved: MutableLiveData<Boolean?> = MutableLiveData(null)
    init {
        playerInteractor.provideUrl(url)
    }
    fun getIsTrackSaved():LiveData<Boolean?> = isTrackSaved
    fun checkIsTrackSaved(currentTrackItem:Track){
        isTrackSaved.postValue(null)
        viewModelScope.launch {
            dataBaseInteractor.getFavoritesList().collect{
                isTrackSaved.postValue(it.contains(currentTrackItem))
            }
        }
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

    fun isTrackSavedToFavorites(): Boolean? {
        return isTrackSaved.value
    }

    fun removeTrackFromFavorites(currentTrackItem: Track) {
        viewModelScope.launch {
            dataBaseInteractor.removeTrackFromFavorites(currentTrackItem)

        }
    }

    fun addTrackToFavorites(currentTrackItem: Track) {
        viewModelScope.launch {
            dataBaseInteractor.addTrackToFavorites(currentTrackItem)
        }
    }
}

