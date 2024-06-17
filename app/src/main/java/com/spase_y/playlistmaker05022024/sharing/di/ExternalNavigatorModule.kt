package com.spase_y.playlistmaker05022024.sharing.di

import com.spase_y.playlistmaker05022024.sharing.data.ExternalNavigatorImpl
import com.spase_y.playlistmaker05022024.sharing.domain.api.ExternalNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val externalNavigatorModule = module {
    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}
