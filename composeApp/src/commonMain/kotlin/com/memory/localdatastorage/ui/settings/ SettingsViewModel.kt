package com.memory.localdatastorage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memory.localdatastorage.data.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {

    val theme: StateFlow<String> = settingsManager.themeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "light")

    val sortOrder: StateFlow<String> = settingsManager.sortOrderFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "created_at")

    fun setTheme(theme: String) {
        viewModelScope.launch {
            settingsManager.setTheme(theme)
        }
    }

    fun setSortOrder(sortOrder: String) {
        viewModelScope.launch {
            settingsManager.setSortOrder(sortOrder)
        }
    }
}