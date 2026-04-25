package com.memory.localdatastorage.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.SettingsManager
import com.memory.localdatastorage.ui.detail.NoteDetailScreen
import com.memory.localdatastorage.ui.home.HomeScreen
import com.memory.localdatastorage.ui.search.SearchScreen
import com.memory.localdatastorage.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    repository: NoteRepository,
    settingsManager: SettingsManager
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                repository = repository,
                settingsManager = settingsManager,
                onNoteClick = { id -> navController.navigate("detail/$id") },
                onAddClick = { navController.navigate("detail/new") },
                onSearchClick = { navController.navigate("search") },
                onSettingsClick = { navController.navigate("settings") }
            )
        }
        composable("detail/{noteId}") { backStack ->
            val noteId = backStack.arguments?.getString("noteId")
            NoteDetailScreen(
                noteId = if (noteId == "new") null else noteId?.toLongOrNull(),
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }
        composable("search") {
            SearchScreen(
                repository = repository,
                onNoteClick = { id -> navController.navigate("detail/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                settingsManager = settingsManager,
                onBack = { navController.popBackStack() }
            )
        }
    }
}