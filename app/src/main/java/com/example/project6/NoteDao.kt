package com.example.project6

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Data Access Object (DAO) for managing Note entities in the database
 */
@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)
    @Update
    suspend fun update(note: Note)
    @Delete
    suspend fun delete(note: Note)
    /**
     * Retrieves a specific Note by its unique identifier
     */
    @Query("SELECT * FROM note_table WHERE noteId = :key")
    fun get(key: Long): LiveData<Note>
    /**
     * Retrieves all Notes from the database in descending order by ID
     */
    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAll(): LiveData<List<Note>>
}