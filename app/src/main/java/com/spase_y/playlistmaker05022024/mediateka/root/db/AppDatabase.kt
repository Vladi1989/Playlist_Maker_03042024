package com.spase_y.playlistmaker05022024.mediateka.root.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spase_y.playlistmaker05022024.mediateka.favorites.data.room.FavoriteTrackDao
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.room.PlaylistDao
import com.spase_y.playlistmaker05022024.search.domain.model.Track

@TypeConverters(Converters::class)
@Database(entities = [Track::class, Playlist::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTrackDao(): FavoriteTrackDao
    abstract fun playlistDao(): PlaylistDao


}
