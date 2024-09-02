package com.spase_y.playlistmaker05022024.player.domain.impl

import com.spase_y.playlistmaker05022024.player.domain.api.FormaterInteractor
import java.text.SimpleDateFormat
import java.util.*

class FormaterInteractorImpl : FormaterInteractor {
    override fun formatText(long: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(long)
    }

    override fun formatUrlImage(string: String): String {
        return string.replaceAfterLast('/', "512x512bb.jpg")
    }

    override fun formatYear(string: String): String {
        return string.replaceAfter("-", "").replace("-", "")
    }

    override fun roundToNearestThousand(milliseconds: Int): Int {
        val remainder = milliseconds % 1000
        return if (remainder < 500) {
            milliseconds - remainder
        } else {
            milliseconds + (1000 - remainder)
        }
    }

}