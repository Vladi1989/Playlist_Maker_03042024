package com.spase_y.playlistmaker05022024.mediateka.favorites.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.favorites.domain.api.DataBaseInteractor
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.utils.CLICK_DEBOUNCE_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MedialibraryFavoritesViewModel(
    private val dataBaseInteractor: DataBaseInteractor
) : ViewModel() {
    private var isClickAllowed = true
    private val favoriteTracks: MutableLiveData<List<Track>> = MutableLiveData()
    init {
        getFavorites()
    }
    fun getFavorites() {
        viewModelScope.launch {
            dataBaseInteractor.getFavoritesList().collect{list->
                favoriteTracks.postValue(list)
            }
        }
    }
    fun getFavoritesTracks():LiveData<List<Track>> = favoriteTracks

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
}