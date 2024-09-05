package com.riezki.mvinote.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    var title: String,
    var description: String,
    var imageUrl: String,

    var dateAdded: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
