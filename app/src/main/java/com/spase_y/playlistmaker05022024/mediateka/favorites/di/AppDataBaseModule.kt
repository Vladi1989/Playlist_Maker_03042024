package com.spase_y.playlistmaker05022024.mediateka.favorites.di

import androidx.room.Room
import com.spase_y.playlistmaker05022024.mediateka.favorites.data.room.AppDatabase
import com.spase_y.playlistmaker05022024.mediateka.favorites.data.room.FavoriteTrackDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDatabaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
    single<FavoriteTrackDao>{ get<AppDatabase>().favoriteTrackDao() }
}