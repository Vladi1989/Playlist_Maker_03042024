package com.spase_y.playlistmaker05022024.search.data

import com.spase_y.playlistmaker05022024.search.domain.model.RequestResult


interface NetworkClient {
    suspend fun doRequest(text:String): RequestResult
}