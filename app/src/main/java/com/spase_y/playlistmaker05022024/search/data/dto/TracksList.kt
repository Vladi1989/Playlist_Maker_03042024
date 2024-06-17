package com.spase_y.playlistmaker05022024.search.data.dto

import com.spase_y.playlistmaker05022024.search.domain.model.Track

data class TracksList(
   val resultCount: Int,
   val results: List<Track>
)
