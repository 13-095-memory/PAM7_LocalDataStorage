package com.memory.localdatastorage.data

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

class SettingsManager(settings: ObservableSettings) {

    private val flowSettings: FlowSettings = settings.toFlowSettings()

    companion object {
        private const val KEY_THEME = "app_theme"
        private const val KEY_SORT_ORDER = "sort_order"
    }

    val themeFlow: Flow<String> = flowSettings.getStringFlow(KEY_THEME, "light")
    val sortOrderFlow: Flow<String> = flowSettings.getStringFlow(KEY_SORT_ORDER, "created_at")

    suspend fun setTheme(theme: String) {
        flowSettings.putString(KEY_THEME, theme)
    }

    suspend fun setSortOrder(sortOrder: String) {
        flowSettings.putString(KEY_SORT_ORDER, sortOrder)
    }
}