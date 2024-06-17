package com.spase_y.playlistmaker05022024.sharing.domain.api

import com.spase_y.playlistmaker05022024.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun openLink(link: String)
    fun openEmail(emailData: EmailData)
}