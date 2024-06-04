package com.spase_y.playlistmaker05022024.sharing.domain.impl

import com.spase_y.playlistmaker05022024.sharing.domain.api.SharingInteractor
import com.spase_y.playlistmaker05022024.sharing.domain.api.ExternalNavigator
import com.spase_y.playlistmaker05022024.sharing.domain.model.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return "https://practicum.yandex.ru/"
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData("vlad@gmail.com",
            "Спасибо разработчикам и разработчицам за крутое приложение!",
            "Сообщение разработчикам и разработчицам приложения Playlist Maker")
    }

    private fun getTermsLink(): String {
        return "https://yandex.ru/legal/practicum_offer/"
    }
}