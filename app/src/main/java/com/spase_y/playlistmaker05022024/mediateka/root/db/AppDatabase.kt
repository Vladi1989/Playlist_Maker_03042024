package com.spase_y.playlistmaker05022024.mediateka.root.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
class Converters {

    @TypeConverter
    fun fromTrackList(trackList: List<Track>): String {
        return Gson().toJson(trackList)
    }

    @TypeConverter
    fun toTrackList(trackListString: String): List<Track> {
        val listType = object : TypeToken<List<Track>>() {}.type
        return Gson().fromJson(trackListString, listType)
    }
}
