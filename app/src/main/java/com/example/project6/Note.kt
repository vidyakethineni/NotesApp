package com.example.project6;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a Note in the database
 */
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
    @ColumnInfo(name = "note_name")
    var noteName: String = "",
    @ColumnInfo(name = "note_description")
    var noteDescription: String = ""
)
