package com.spase_y.playlistmaker05022024.domain.api

interface FormaterInteractor {
    fun formatText(long: Long): String
    fun formatUrlImage(string: String): String
    fun formatYear(string: String): String
    fun roundToNearestThousand(milliseconds: Int): Int
}