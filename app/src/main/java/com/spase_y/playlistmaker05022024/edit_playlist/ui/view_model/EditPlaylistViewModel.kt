package com.spase_y.playlistmaker05022024.edit_playlist.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import kotlinx.coroutines.launch

class EditPlaylistViewModel(
    private val dbInteractor: PlaylistInteractor
): ViewModel() {
    fun editPlaylist(oldPlaylist: Playlist,newPlaylist: Playlist){
        viewModelScope.launch {
            dbInteractor.removePlaylist(oldPlaylist)
            dbInteractor.addPlaylist(newPlaylist)
        }

    }
}
