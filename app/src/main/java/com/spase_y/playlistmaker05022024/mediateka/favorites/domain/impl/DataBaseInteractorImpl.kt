package com.spase_y.playlistmaker05022024.mediateka.favorites.domain.impl

import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseInteractor
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseRepository
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

class DataBaseInteractorImpl(private val repository: DataBaseRepository):DataBaseInteractor {


    override suspend fun removeTrackFromFavorites(currentTrackItem: Track) {
        repository.removeTrackFromFavorites(currentTrackItem)
    }

    override suspend fun addTrackToFavorites(currentTrackItem: Track) {
        repository.addTrackToFavorites(currentTrackItem)
    }

    override fun getFavoritesList(): Flow<List<Track>> {
        return repository.getFavoritesList()
    }

}