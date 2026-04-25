package com.memory.localdatastorage.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memory.localdatastorage.data.NoteRepository
import com.memory.localdatastorage.data.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    fun loadNote(id: Long) {
        viewModelScope.launch {
            _note.value = repository.getNoteById(id)
        }
    }

    fun saveNote(
        id: Long?,
        title: String,
        content: String,
        subject: String?,
        priority: String?
    ) {
        viewModelScope.launch {
            if (id == null) {
                repository.insertNote(title, content, subject, priority)
            } else {
                repository.updateNote(id, title, content, subject, priority)
            }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }
}