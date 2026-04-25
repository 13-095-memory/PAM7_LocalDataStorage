package com.memory.localdatastorage.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.memory.localdatastorage.data.SettingsManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsManager: SettingsManager,
    onBack: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: kotlin.reflect.KClass<T>, extras: androidx.lifecycle.viewmodel.CreationExtras): T {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(settingsManager) as T
            }
        }
    )

    val theme by viewModel.theme.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Tema", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = theme == "light",
                    onClick = { viewModel.setTheme("light") },
                    label = { Text("Light") }
                )
                FilterChip(
                    selected = theme == "dark",
                    onClick = { viewModel.setTheme("dark") },
                    label = { Text("Dark") }
                )
            }

            Divider()

            Text("Urutkan Berdasarkan", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = sortOrder == "created_at",
                    onClick = { viewModel.setSortOrder("created_at") },
                    label = { Text("Terbaru Dibuat") }
                )
                FilterChip(
                    selected = sortOrder == "updated_at",
                    onClick = { viewModel.setSortOrder("updated_at") },
                    label = { Text("Terbaru Diedit") }
                )
            }
        }
    }
}