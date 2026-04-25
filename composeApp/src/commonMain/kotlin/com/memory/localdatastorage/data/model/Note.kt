package com.memory.localdatastorage.data.model

data class Note(
    val id: Long = 0L,
    val title: String,
    val content: String,
    val subject: String? = null,
    val priority: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)