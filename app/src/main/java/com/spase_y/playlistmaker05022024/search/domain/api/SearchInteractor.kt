package com.spase_y.playlistmaker05022024.search.domain.api

import com.spase_y.playlistmaker05022024.search.domain.model.RequestResult
import com.spase_y.playlistmaker05022024.search.domain.model.Track

interface SearchInteractor {
    fun addItem(track: Track)
    fun deleteItem(track: Track)
    fun deleteAllItems()
    fun getAllItems(): MutableList<Track>
    suspend fun doRequest(text: String, param: SearchConsumer)
    interface SearchConsumer {
        fun consume(result: RequestResult)
    }

}