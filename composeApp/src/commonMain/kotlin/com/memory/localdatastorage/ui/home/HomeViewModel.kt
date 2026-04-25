package com.memory.localdatastorage.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.SettingsManager
import com.memory.localdatastorage.data.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val notes: StateFlow<List<Note>> = settingsManager.sortOrderFlow
        .flatMapLatest { sortOrder ->
            if (sortOrder == "updated_at") repository.getAllNotesByUpdated()
            else repository.getAllNotes()
        }
        .onEach { _isLoading.value = false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addNote(title: String, content: String, subject: String?, priority: String?) {
        viewModelScope.launch {
            repository.insertNote(title, content, subject, priority)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }
}