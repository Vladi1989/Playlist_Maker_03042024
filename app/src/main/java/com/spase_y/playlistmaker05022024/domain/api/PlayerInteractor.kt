package com.spase_y.playlistmaker05022024.domain.api

import android.widget.ImageView
import com.bumptech.glide.RequestManager

interface PlayerInteractor {
    fun loadGlideImage(big: String, glide: RequestManager, ivIcon: ImageView)
    fun formatText(long: Long): String
    fun formatUrlImage(string: String): String
    fun formatYear(string: String): String
    fun roundToNearestThousand(milliseconds: Int): Int

}
