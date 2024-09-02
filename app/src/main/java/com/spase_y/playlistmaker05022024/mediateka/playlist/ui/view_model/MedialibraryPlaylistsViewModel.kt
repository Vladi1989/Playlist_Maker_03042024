package com.spase_y.playlistmaker05022024.mediateka.playlist.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.mediateka.playlist.domain.api.PlaylistInteractor
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.model.MedialibraryPlaylistScreenState
import kotlinx.coroutines.launch

class MedialibraryPlaylistsViewModel(
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {
    private val screenStateLD = MutableLiveData<MedialibraryPlaylistScreenState>()
    fun getScreenStateLD():LiveData<MedialibraryPlaylistScreenState> = screenStateLD

    fun loadMyPlaylists() {
        screenStateLD.postValue(MedialibraryPlaylistScreenState.Loading)
        viewModelScope.launch {
            playlistInteractor.getPlaylistList().collect{
                if(it.size == 0){
                    screenStateLD.postValue(MedialibraryPlaylistScreenState.Empty)
                }
                else{
                    screenStateLD.postValue(MedialibraryPlaylistScreenState.Result(it))
                }
            }

        }
    }

}