package com.spase_y.playlistmaker05022024.settings.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.ImageView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.spase_y.playlistmaker05022024.utils.App
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.settings.ui.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
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