package com.spase_y.playlistmaker05022024.mediateka.root.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spase_y.playlistmaker05022024.search.domain.model.Track

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