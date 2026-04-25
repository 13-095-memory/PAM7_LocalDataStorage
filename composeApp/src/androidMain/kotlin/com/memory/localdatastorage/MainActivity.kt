package com.memory.localdatastorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.SettingsManager
import com.memory.localdatastorage.data.database.DatabaseDriverFactory
import com.russhwolf.settings.SharedPreferencesSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val driverFactory = DatabaseDriverFactory(this)
        val repository = NoteRepository(driverFactory)
        val settings = SharedPreferencesSettings(
            getSharedPreferences("notes_settings", MODE_PRIVATE)
        )
        val settingsManager = SettingsManager(settings)

        setContent {
            App(
                repository = repository,
                settingsManager = settingsManager
            )
        }
    }
}