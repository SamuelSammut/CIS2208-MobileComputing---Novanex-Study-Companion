package com.example.novanex_studycompanion.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.novanex_studycompanion.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain ViewModel instance
        val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup UI elements
        val textView = binding.textSettings
        val switchDarkMode = binding.switchDarkMode

        // Observe ViewModel text LiveData
        settingsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Load saved preferences
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val isDarkModeEnabled = sharedPreferences.getBoolean("DARK_MODE", false)
        switchDarkMode.isChecked = isDarkModeEnabled

        // Set up switch listener for dark mode toggle
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            // Save preference
            with(sharedPreferences.edit()) {
                putBoolean("DARK_MODE", isChecked)
                apply()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
