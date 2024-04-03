package com.spase_y.playlistmaker05022024

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String) : Call<TracksList>
}
