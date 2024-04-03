package com.spase_y.playlistmaker05022024

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backFromSetting = findViewById<ImageButton>(R.id.buttonBack)
        backFromSetting.setOnClickListener {
            finish()
        }
        val btnShare = findViewById<ImageView>(R.id.btnShare)
        btnShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shared_text));
            startActivity(Intent.createChooser(shareIntent,getString(R.string.shared_text)))
        }

        val btnSupport = findViewById<ImageView>(R.id.btnSupport)
        btnSupport.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.my_mail)))
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.my_app_share_text))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.my_app_share_theme))
            startActivity(Intent.createChooser(shareIntent,getString(R.string.my_app_share_theme)))
        }
        val btnAgreement = findViewById<ImageView>(R.id.btnUserAgreement)
        btnAgreement.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.practicun_offer)))
            startActivity(intent)
        }
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.isChecked = (applicationContext as App).darkTheme
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
        }
    }
}