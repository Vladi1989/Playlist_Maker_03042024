package com.spase_y.playlistmaker05022024.player.ui.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseInteractor
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.model.MedialibraryPlaylistScreenState
import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import com.spase_y.playlistmaker05022024.player.domain.api.PlayerInteractor
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val playerInteractor: PlayerInteractor,
    private val formaterInteractor: FormaterInteractor,
    private val dataBaseInteractor: DataBaseInteractor,
    private val url: String
) : ViewModel() {
    private var trackId = -1
    private val isTrackSaved: MutableLiveData<Boolean?> = MutableLiveData(null)
    private val screenStateLD = MutableLiveData<MedialibraryPlaylistScreenState>()
    fun getScreenStateLD(): LiveData<MedialibraryPlaylistScreenState> = screenStateLD

    init {
        playerInteractor.provideUrl(url)
    }

    fun getIsTrackSaved(): LiveData<Boolean?> = isTrackSaved
    fun checkIsTrackSaved(currentTrackItem: Track) {
        viewModelScope.launch {
            dataBaseInteractor.getFavoritesList().collect {
                isTrackSaved.postValue(containsTrackIgnoringId(it, currentTrackItem))
            }
        }
    }

    fun containsTrackIgnoringId(trackList: List<Track>, trackToCheck: Track): Boolean {
        var isContains = false
        trackList.forEach { track ->
            if (track.previewUrl == trackToCheck.previewUrl &&
                track.trackName == trackToCheck.trackName &&
                track.artistName == trackToCheck.artistName &&
                track.trackTimeMillis == trackToCheck.trackTimeMillis &&
                track.artworkUrl100 == trackToCheck.artworkUrl100 &&
                track.collectionName == trackToCheck.collectionName &&
                track.releaseDate == trackToCheck.releaseDate &&
                track.primaryGenreName == trackToCheck.primaryGenreName &&
                track.country == trackToCheck.country
            ) {
                trackId = track.trackId
                isContains = true
                return isContains
            }
        }
        return isContains
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


    fun removeTrackFromFavorites(currentTrackItem: Track) {
        val trackToDelete = currentTrackItem.copy(trackId = trackId)
        viewModelScope.launch {
            dataBaseInteractor.removeTrackFromFavorites(trackToDelete)
            isTrackSaved.postValue(false)
        }
    }

    fun addTrackToFavorites(currentTrackItem: Track) {
        viewModelScope.launch {
            dataBaseInteractor.addTrackToFavorites(currentTrackItem)
            isTrackSaved.postValue(true)
        }
    }

    fun loadMyPlaylists() {
        screenStateLD.postValue(MedialibraryPlaylistScreenState.Loading)
        viewModelScope.launch {
            playlistInteractor.getPlaylistList().collect {
                if (it.size == 0) {
                    screenStateLD.postValue(MedialibraryPlaylistScreenState.Empty)
                } else {
                    screenStateLD.postValue(MedialibraryPlaylistScreenState.Result(it))
                }
            }
        }
    }

    fun addTrackToPlaylist(currentTrackItem: Track, it: Playlist) {
        val newList = it.trackList.toMutableList()
        newList.add(currentTrackItem)
        val copyPlaylist = it.copy(trackList = newList)
        viewModelScope.launch {
            playlistInteractor.removePlaylist(it)
            playlistInteractor.addPlaylist(copyPlaylist)
        }
    }
}

