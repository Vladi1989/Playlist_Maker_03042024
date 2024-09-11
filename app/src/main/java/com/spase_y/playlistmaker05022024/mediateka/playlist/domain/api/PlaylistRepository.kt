package com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api

import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun removePlaylist(playlist: Playlist)

    suspend fun addPlaylist(playlist: Playlist)

    fun getPlaylistList(): Flow<List<Playlist>>
}