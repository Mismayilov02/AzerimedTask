package com.mismayilov.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.mismayilov.core.managers.NavigationManager
import com.mismayilov.data.local.SharedPreferencesManager
import com.mismayilov.manager.LanguageManager
import com.mismayilov.settings.databinding.FragmentSettingsBinding
import com.mismayilov.settings.view.LanguageBottomSheetDialog

class SettingsFragment : Fragment() {
    private var binding: FragmentSettingsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NavigationManager).bottomNavigationVisibility(true)
        initLanguage()
        initDarkModeSwitch()
    }
    private fun initLanguage() {
        binding?.apply {
            val languageCode = SharedPreferencesManager.getValue("languageCode", "en")
            val languagePosition = LanguageManager.getLanguagePosition(languageCode)
            val languageNames = resources.getStringArray(com.mismayilov.uikit.R.array.languages)[languagePosition]
            languageSubText.text = languageNames
            languageCardView.setOnClickListener {
                val bottomSheetDialog = LanguageBottomSheetDialog(languageCode)
                bottomSheetDialog.show(childFragmentManager, "languageBottomSheet")
            }
        }
    }

    private fun initDarkModeSwitch() {
        binding!!.darkModeSwitch.isChecked = SharedPreferencesManager.getValue("darkMode", false)
        binding!!.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            SharedPreferencesManager.setValue("darkMode", isChecked)
            AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}