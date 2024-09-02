package com.spase_y.playlistmaker05022024.search.ui

import com.spase_y.playlistmaker05022024.search.domain.model.Track

sealed interface TrackScreenState {

    object Loading : TrackScreenState

    object Error : TrackScreenState
    object Empty : TrackScreenState


    data class History(
        val tracks: List<Track>
    ) : TrackScreenState

    data class Content(
        val tracks: List<Track>
    ) : TrackScreenState
}