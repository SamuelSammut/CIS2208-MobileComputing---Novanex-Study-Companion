package com.example.novanex_studycompanion.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    // LiveData holding the text for the settings page
    private val _text = MutableLiveData<String>().apply {
        value = "Settings"
    }
    val text: LiveData<String> = _text
}
