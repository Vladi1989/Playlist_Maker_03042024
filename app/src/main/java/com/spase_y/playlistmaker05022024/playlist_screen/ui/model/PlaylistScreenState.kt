package com.spase_y.playlistmaker05022024.playlist_screen.ui.model

import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist

interface PlaylistScreenState {
    class Update(val playlist: Playlist):PlaylistScreenState
}