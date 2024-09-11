package com.spase_y.playlistmaker05022024.edit_playlist.di

import com.spase_y.playlistmaker05022024.edit_playlist.ui.view_model.EditPlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editPlaylistModule = module {
    viewModel<EditPlaylistViewModel>{
        EditPlaylistViewModel(get())
    }
}