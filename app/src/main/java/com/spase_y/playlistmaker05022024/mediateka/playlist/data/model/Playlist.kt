package com.spase_y.playlistmaker05022024.mediateka.playlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spase_y.playlistmaker05022024.search.domain.model.Track

@Entity("playlists")
data class Playlist(
    val imageUrl: String,
    val playlistName: String,
    val trackList: List<Track>,
    val description: String,
    @PrimaryKey(autoGenerate = true) val playlistId: Int = 0
)