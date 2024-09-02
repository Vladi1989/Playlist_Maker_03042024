package com.spase_y.playlistmaker05022024.search.domain.model

sealed interface RequestResult {
    object Error : RequestResult
    data class Content(
        val listTracks: List<Track>
    ) : RequestResult
}
