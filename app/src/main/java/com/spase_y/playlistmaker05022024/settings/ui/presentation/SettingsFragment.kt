package com.spase_y.playlistmaker05022024.settings.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.settings.ui.view_model.SettingsViewModel
import com.spase_y.playlistmaker05022024.utils.App
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    private val viewModel: SettingsViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnShare = requireView().findViewById<ImageView>(R.id.btnShare)
        btnShare.setOnClickListener {
            viewModel.shareApp()
        }

        val btnSupport = requireView().findViewById<ImageView>(R.id.btnSupport)
        btnSupport.setOnClickListener {
            viewModel.openSupport()
        }
        val btnAgreement = requireView().findViewById<ImageView>(R.id.btnUserAgreement)
        btnAgreement.setOnClickListener {
            viewModel.openTerms()
        }
        val themeSwitcher = requireView().findViewById<SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.isChecked = viewModel.getThemeSettings().isDarkTheme
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            viewModel.updateThemeSetting(viewModel.getThemeSettings().copy(isDarkTheme = checked))
            (requireContext().applicationContext as App).switchTheme(checked)
        }
    }
}