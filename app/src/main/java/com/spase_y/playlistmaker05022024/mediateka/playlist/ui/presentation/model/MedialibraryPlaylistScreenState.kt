package com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.model

import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist


interface MedialibraryPlaylistScreenState {
    object Empty: MedialibraryPlaylistScreenState
    object Loading: MedialibraryPlaylistScreenState

    class Result(val list: List<Playlist>): MedialibraryPlaylistScreenState
}