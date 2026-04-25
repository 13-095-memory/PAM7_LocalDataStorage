package com.memory.localdatastorage

import androidx.compose.runtime.*
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.SettingsManager
import com.memory.localdatastorage.ui.navigation.NavGraph
import com.memory.localdatastorage.ui.theme.NotesAppTheme  // ← tambah ini

@Composable
fun App(
    repository: NoteRepository,
    settingsManager: SettingsManager
) {
    val theme by settingsManager.themeFlow.collectAsState(initial = "light")

    NotesAppTheme(darkTheme = theme == "dark") {
        NavGraph(
            repository = repository,
            settingsManager = settingsManager
        )
    }
}