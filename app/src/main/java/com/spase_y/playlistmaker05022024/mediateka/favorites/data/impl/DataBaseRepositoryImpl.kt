package com.spase_y.playlistmaker05022024.mediateka.favorites.data.impl

import com.spase_y.playlistmaker05022024.mediateka.favorites.data.room.FavoriteTrackDao
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseRepository
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

class DataBaseRepositoryImpl(private val dao: FavoriteTrackDao):DataBaseRepository {


    override suspend fun removeTrackFromFavorites(currentTrackItem: Track) {
        dao.deleteTrack(currentTrackItem)
    }

    override suspend fun addTrackToFavorites(currentTrackItem: Track) {
        dao.insertTrack(currentTrackItem)
    }

    override  fun getFavoritesList(): Flow<List<Track>> {
        return dao.getAllFavoriteTracks()
    }
}