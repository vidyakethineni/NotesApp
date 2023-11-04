package com.example.project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel for managing notes data and related operations
 */
class NotesViewModel(val dao: NoteDao) : ViewModel() {
    var newNoteName = ""
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote

    /**
     * Coroutine function to add a new note to the database
     */
    suspend fun addNote() {
        viewModelScope.launch {
            val note = Note()
            note.noteName = newNoteName
            dao.insert(note)
        }
    }
    /**
     * Coroutine function to delete a note from the database by its ID
     */
    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            val note = Note()
            note.noteId = noteId
            dao.delete(note)
        }
    }
    /**
     * Method triggered when a note is clicked for navigation purposes
     */
    fun onNoteClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }
    /**
     * Method invoked after note navigation is completed
     */
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
}