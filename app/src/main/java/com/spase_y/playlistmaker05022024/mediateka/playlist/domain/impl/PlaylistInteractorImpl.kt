package com.spase_y.playlistmaker05022024.mediateka.playlist.domain.impl

import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(private val repository: PlaylistRepository):PlaylistInteractor {
    override suspend fun removePlaylist(playlist: Playlist) {
        repository.removePlaylist(playlist)
    }

    override suspend fun addPlaylist(playlist: Playlist) {
        repository.addPlaylist(playlist)
    }

    override fun getPlaylistList(): Flow<List<Playlist>> {
        return repository.getPlaylistList()
    }
}