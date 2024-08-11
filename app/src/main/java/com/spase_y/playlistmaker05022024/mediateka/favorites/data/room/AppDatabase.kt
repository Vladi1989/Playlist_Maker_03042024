package com.spase_y.playlistmaker05022024.mediateka.favorites.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import org.koin.dsl.module

@Database(entities = [Track::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTrackDao(): FavoriteTrackDao


}
