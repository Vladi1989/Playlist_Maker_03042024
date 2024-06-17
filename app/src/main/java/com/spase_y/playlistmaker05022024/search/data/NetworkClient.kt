package com.spase_y.playlistmaker05022024.search.data

import com.spase_y.playlistmaker05022024.search.domain.model.RequestResult


interface NetworkClient {
    fun doRequest(text:String): RequestResult
}