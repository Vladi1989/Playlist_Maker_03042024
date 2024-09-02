package com.spase_y.playlistmaker05022024.mediateka.playlist.data.impl

import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.room.PlaylistDao
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistDatabaseRepositoryImpl(private val dao: PlaylistDao):PlaylistRepository {
    override suspend fun removePlaylist(playlist: Playlist) {
        dao.deletePlaylist(playlist)
    }

    override suspend fun addPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlist)
    }

    override fun getPlaylistList(): Flow<List<Playlist>> {
        return dao.getAllPlaylist()
    }

}