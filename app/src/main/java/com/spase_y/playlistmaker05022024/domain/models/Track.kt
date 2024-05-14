package com.spase_y.playlistmaker05022024.domain.models

data class Track(
    val previewUrl:String, 
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String
)


