package com.riezki.mvinote.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author riezkymaisyar
 */

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDb : RoomDatabase() {
    abstract val noteDao: NoteDao

}