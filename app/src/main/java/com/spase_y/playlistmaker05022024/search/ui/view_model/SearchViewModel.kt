package com.spase_y.playlistmaker05022024.search.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.search.domain.api.SearchInteractor
import com.spase_y.playlistmaker05022024.search.domain.model.RequestResult
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.search.ui.TrackScreenState
import kotlinx.coroutines.*

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {
    private val screenStateLD = MutableLiveData<TrackScreenState>(TrackScreenState.History(searchInteractor.getAllItems()))
    private var isClickAllowed = true

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 2000L
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }



    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }
    private var searchJob: Job? = null
    fun searchDebounce(text: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            makeRequest(text)
        }
    }

    fun deleteAllItems() {
        searchInteractor.deleteAllItems()
    }

    fun getAllItems(): List<Track> {
        return searchInteractor.getAllItems()
    }

    fun deleteItem(it: Track) {
        searchInteractor.deleteItem(it)
    }

    fun addItem(it: Track) {
        searchInteractor.addItem(it)
    }

    fun makeRequest(searchText: String) {
        if (searchText.isNullOrEmpty()) {
            screenStateLD.postValue(TrackScreenState.History(searchInteractor.getAllItems()))
            return
        }
        screenStateLD.postValue(TrackScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            searchInteractor.doRequest(searchText, object : SearchInteractor.SearchConsumer {
                override fun consume(result: RequestResult) {
                    when (result) {
                        is RequestResult.Error -> {
                            screenStateLD.postValue(TrackScreenState.Error)
                        }
                        is RequestResult.Content -> {
                            if (result.listTracks.isEmpty()) {
                                screenStateLD.postValue(TrackScreenState.Empty)
                            } else {
                                screenStateLD.postValue(TrackScreenState.Content(result.listTracks))
                            }
                        }
                    }
                }
            })
        }
    }

    fun getScreenStateLD(): LiveData<TrackScreenState> = screenStateLD

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}