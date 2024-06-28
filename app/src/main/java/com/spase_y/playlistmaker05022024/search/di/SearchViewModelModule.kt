package com.spase_y.playlistmaker05022024.search.di

import com.spase_y.playlistmaker05022024.search.ui.view_model.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel<SearchViewModel>{
        SearchViewModel(get())
    }
}