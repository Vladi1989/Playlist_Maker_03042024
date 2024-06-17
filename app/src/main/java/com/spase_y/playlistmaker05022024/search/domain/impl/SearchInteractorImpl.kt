package com.spase_y.playlistmaker05022024.search.domain.impl

import com.spase_y.playlistmaker05022024.search.domain.api.SearchInteractor
import com.spase_y.playlistmaker05022024.search.domain.api.SearchRepository
import com.spase_y.playlistmaker05022024.search.domain.model.RequestResult
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import java.util.concurrent.Executors

class SearchInteractorImpl(private val repository:SearchRepository):SearchInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun addItem(track: Track) {
        repository.addItem(track)
    }

    override fun deleteItem(track: Track) {
        repository.deleteItem(track)
    }

    override fun deleteAllItems() {
        repository.deleteAllItems()
    }

    override fun getAllItems(): MutableList<Track> {
        return repository.getAllItems()
    }

    override fun doRequest(text:String, param: SearchInteractor.SearchConsumer){
        executor.execute {
            val result = repository.doRequest(text)
            param.consume(result)
        }
    }

}