package com.spase_y.playlistmaker05022024.settings.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.spase_y.playlistmaker05022024.utils.App
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.creator.Creator
import com.spase_y.playlistmaker05022024.settings.domain.model.ThemeSettings
import com.spase_y.playlistmaker05022024.settings.ui.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        viewModel = ViewModelProvider(this, SettingsViewModel.getViewModelFactory())[SettingsViewModel::class.java]
        val backFromSetting = findViewById<ImageButton>(R.id.buttonBack)
        backFromSetting.setOnClickListener {
            finish()
        }
        val btnShare = findViewById<ImageView>(R.id.btnShare)
        btnShare.setOnClickListener {
            viewModel.shareApp()
        }

        val btnSupport = findViewById<ImageView>(R.id.btnSupport)
        btnSupport.setOnClickListener {
            viewModel.openSupport()
        }
        val btnAgreement = findViewById<ImageView>(R.id.btnUserAgreement)
        btnAgreement.setOnClickListener {
            viewModel.openTerms()
        }
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.isChecked = viewModel.getThemeSettings().isDarkTheme
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            viewModel.updateThemeSetting(viewModel.getThemeSettings().copy(isDarkTheme = checked))
            (applicationContext as App).switchTheme(checked)
        }
    }
}