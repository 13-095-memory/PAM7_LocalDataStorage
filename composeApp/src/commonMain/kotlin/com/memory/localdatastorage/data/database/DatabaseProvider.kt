package com.memory.localdatastorage.data.database

import com.memory.localdatastorage.db.NotesDatabase

object DatabaseProvider {
    private var database: NotesDatabase? = null

    fun getDatabase(driverFactory: DatabaseDriverFactory): NotesDatabase {
        return database ?: NotesDatabase(
            driverFactory.createDriver()
        ).also { database = it }
    }
}