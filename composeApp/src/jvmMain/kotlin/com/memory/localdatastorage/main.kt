package com.memory.localdatastorage

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.SettingsManager
import com.memory.localdatastorage.data.database.DatabaseDriverFactory
import com.russhwolf.settings.PreferencesSettings

fun main() = application {
    val driverFactory = DatabaseDriverFactory()
    val repository = NoteRepository(driverFactory)
    val settings = PreferencesSettings.Factory().create("notes_settings")
    val settingsManager = SettingsManager(settings)

    Window(onCloseRequest = ::exitApplication, title = "Notes App") {
        App(
            repository = repository,
            settingsManager = settingsManager
        )
    }
}