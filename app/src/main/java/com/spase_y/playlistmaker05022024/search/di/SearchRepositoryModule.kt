package com.spase_y.playlistmaker05022024.search.di

import com.spase_y.playlistmaker05022024.search.data.NetworkClient
import com.spase_y.playlistmaker05022024.search.data.SearchRepositoryImpl
import com.spase_y.playlistmaker05022024.search.data.db.LocalStorage
import com.spase_y.playlistmaker05022024.search.data.network.RetrofitNetworkClient
import com.spase_y.playlistmaker05022024.search.domain.api.SearchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val searchRepositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(),get())
    }
    single {
        LocalStorage(get())
    }
    single<NetworkClient> {
        RetrofitNetworkClient(androidContext())
    }
}