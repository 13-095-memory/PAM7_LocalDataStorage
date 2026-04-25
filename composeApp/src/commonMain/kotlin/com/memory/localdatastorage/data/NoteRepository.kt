package com.memory.localdatastorage.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.memory.localdatastorage.data.database.DatabaseProvider
import com.memory.localdatastorage.data.database.DatabaseDriverFactory
import com.memory.localdatastorage.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NoteRepository(driverFactory: DatabaseDriverFactory) {

    private val database = DatabaseProvider.getDatabase(driverFactory)
    private val queries = database.noteQueries

    fun getAllNotes(): Flow<List<Note>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toNote() } }
    }

    fun getAllNotesByUpdated(): Flow<List<Note>> {
        return queries.selectAllByUpdated()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toNote() } }
    }

    suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOneOrNull()?.toNote()
        }
    }

    suspend fun insertNote(
        title: String,
        content: String,
        subject: String? = null,
        priority: String? = null
    ) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.insert(title, content, subject, priority, now, now)
        }
    }

    suspend fun updateNote(
        id: Long,
        title: String,
        content: String,
        subject: String? = null,
        priority: String? = null
    ) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.update(title, content, subject, priority, now, id)
        }
    }

    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        val likeQuery = "%$query%"
        return queries.search(likeQuery, likeQuery)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toNote() } }
    }

    private fun com.memory.localdatastorage.db.Note.toNote() = Note(
        id = id,
        title = title,
        content = content,
        subject = subject,
        priority = priority,
        createdAt = created_at,
        updatedAt = updated_at
    )
}