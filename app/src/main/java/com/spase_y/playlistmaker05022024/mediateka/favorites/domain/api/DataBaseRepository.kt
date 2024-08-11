package com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api

import com.spase_y.playlistmaker05022024.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {
    suspend fun removeTrackFromFavorites(currentTrackItem: Track)
    suspend fun addTrackToFavorites(currentTrackItem: Track)
    fun getFavoritesList(): Flow<List<Track>>
}