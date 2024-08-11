package com.spase_y.playlistmaker05022024.search.data

import com.spase_y.playlistmaker05022024.search.data.db.LocalStorage
import com.spase_y.playlistmaker05022024.search.domain.api.SearchRepository
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.search.data.dto.TracksList
import com.spase_y.playlistmaker05022024.search.domain.model.RequestResult

class SearchRepositoryImpl(
    private val localStorage: LocalStorage,
    private val networkClient: NetworkClient
):SearchRepository {
    override fun addItem(track: Track) {
        localStorage.addItem(track)
    }

    override fun deleteItem(track: Track) {
        localStorage.deleteItem(track)
    }

    override fun deleteAllItems() {
        localStorage.deleteAllItems()
    }

    override fun getAllItems(): MutableList<Track> {
        return localStorage.getAllItems()
    }

    override suspend fun doRequest(text: String): RequestResult {
        return networkClient.doRequest(text)
    }



}