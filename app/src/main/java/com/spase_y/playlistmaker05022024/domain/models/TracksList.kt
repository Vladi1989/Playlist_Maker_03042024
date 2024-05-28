package com.spase_y.playlistmaker05022024.domain.models

import com.spase_y.playlistmaker05022024.domain.models.Track

data class TracksList(
   val resultCount: Int,
   val results: List<Track>
)
