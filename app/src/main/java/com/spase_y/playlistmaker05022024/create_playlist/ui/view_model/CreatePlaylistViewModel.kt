package com.spase_y.playlistmaker05022024.create_playlist.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor

):ViewModel() {
    fun addToPlaylists(playlist: Playlist){
        viewModelScope.launch {
            playlistInteractor.addPlaylist(playlist)
        }
    }
}
