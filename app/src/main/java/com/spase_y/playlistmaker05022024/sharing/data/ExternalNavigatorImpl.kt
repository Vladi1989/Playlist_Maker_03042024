package com.spase_y.playlistmaker05022024.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.sharing.domain.api.ExternalNavigator
import com.spase_y.playlistmaker05022024.sharing.domain.model.EmailData

class ExternalNavigatorImpl(private val context: Context):ExternalNavigator {
    override fun shareLink(link: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,link)
        val intentChooser = Intent.createChooser(shareIntent,link)
        intentChooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentChooser)
    }
    override fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    override fun openEmail(emailData: EmailData) {
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse("mailto:")
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailData.email))
        shareIntent.putExtra(Intent.EXTRA_TEXT, emailData.text)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
        val intentChooser = Intent.createChooser(shareIntent,emailData.subject)
        intentChooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentChooser)
    }
}