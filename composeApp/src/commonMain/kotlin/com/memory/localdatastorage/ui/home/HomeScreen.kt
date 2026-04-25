package com.memory.localdatastorage.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.SettingsManager
import com.memory.localdatastorage.ui.components.EmptyState
import com.memory.localdatastorage.ui.components.NoteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    repository: NoteRepository,
    settingsManager: SettingsManager,
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: kotlin.reflect.KClass<T>, extras: androidx.lifecycle.viewmodel.CreationExtras): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository, settingsManager) as T
            }
        }
    )

    val notes by viewModel.notes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes ✨") },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Text("🔍")
                    }
                    IconButton(onClick = onSettingsClick) {
                        Text("⚙️")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                // Loading state
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // Empty state
                notes.isEmpty() -> {
                    EmptyState()
                }
                // Content state
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(notes) { note ->
                            NoteCard(
                                note = note,
                                onClick = { onNoteClick(note.id) },
                                onDelete = { viewModel.deleteNote(note.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}