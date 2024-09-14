package com.spase_y.playlistmaker05022024.playlist_screen.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import com.spase_y.playlistmaker05022024.playlist_screen.ui.model.PlaylistScreenState
import com.spase_y.playlistmaker05022024.playlist_screen.ui.presentation.PlaylistScreenFragment
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.utils.CLICK_DEBOUNCE_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    val playlistDatabaseInteractor: PlaylistInteractor
):ViewModel() {
    private var isClickAllowed = true
    private val screenStateLD = MutableLiveData<PlaylistScreenState>()
    fun getScreenStateLD():LiveData<PlaylistScreenState>{
        return screenStateLD
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    fun deleteTrack(playlist: Playlist, track: Track) {
        val newTrackList = playlist.trackList.toMutableList()
        newTrackList.remove(track)
        val newPlaylist = playlist.copy(trackList = newTrackList)
        viewModelScope.launch {
            playlistDatabaseInteractor.removePlaylist(playlist)
            playlistDatabaseInteractor.addPlaylist(newPlaylist)
            screenStateLD.postValue(PlaylistScreenState.Update(newPlaylist))
        }

    }
    fun deletePlaylist(playlist: Playlist){
        viewModelScope.launch {
            playlistDatabaseInteractor.removePlaylist(playlist)
        }
    }
}
