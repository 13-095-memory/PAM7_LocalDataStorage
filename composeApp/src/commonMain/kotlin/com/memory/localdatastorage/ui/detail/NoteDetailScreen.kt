package com.memory.localdatastorage.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.memory.localdatastorage.data.NoteRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Long?,
    repository: NoteRepository,
    onBack: () -> Unit
) {
    val viewModel: NoteDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: kotlin.reflect.KClass<T>, extras: androidx.lifecycle.viewmodel.CreationExtras): T {
                @Suppress("UNCHECKED_CAST")
                return NoteDetailViewModel(repository) as T
            }
        }
    )

    val note by viewModel.note.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }

    LaunchedEffect(noteId) {
        if (noteId != null) {
            viewModel.loadNote(noteId)
        }
    }

    LaunchedEffect(note) {
        note?.let {
            title = it.title
            content = it.content
            subject = it.subject ?: ""
            priority = it.priority ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Catatan Baru" else "Edit Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                },
                actions = {
                    if (noteId != null) {
                        IconButton(onClick = {
                            viewModel.deleteNote(noteId)
                            onBack()
                        }) {
                            Text("🗑")
                        }
                    }
                    TextButton(onClick = {
                        viewModel.saveNote(
                            noteId, title, content,
                            subject.ifBlank { null },
                            priority.ifBlank { null }
                        )
                        onBack()
                    }) {
                        Text("Simpan")
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Mata Kuliah") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = priority,
                onValueChange = { priority = it },
                label = { Text("Prioritas (tinggi/sedang/rendah)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Isi Catatan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = Int.MAX_VALUE
            )
        }
    }
}