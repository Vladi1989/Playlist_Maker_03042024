package com.spase_y.playlistmaker05022024

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(val sharedPreferences: SharedPreferences) {
    private val gson = Gson()
    fun addItem(track: Track){

        val listOfTracks = getAllItems()
        listOfTracks.add(track)
        sharedPreferences.edit().putString(TRACK_LIST_TAG,gson.toJson(listOfTracks)).apply()

    }
    fun deleteItem(track: Track){
        val listOfTracks = getAllItems()
        listOfTracks.remove(track)
        sharedPreferences.edit().putString(TRACK_LIST_TAG,gson.toJson(listOfTracks)).apply()
    }
    fun deleteAllItems(){
        sharedPreferences.edit().putString(TRACK_LIST_TAG,"[]").apply()
    }
    fun getAllItems():MutableList<Track>{
        val savedJsonString = sharedPreferences.getString(TRACK_LIST_TAG,"[]")
        val typeToken = object : TypeToken<MutableList<Track>>() {}.type
        val listOfTracks = gson.fromJson<MutableList<Track>>(savedJsonString, typeToken)
        return listOfTracks.reversed().toMutableList()
    }
    companion object{
        const val TRACK_LIST_TAG = "Track list"
    }
}