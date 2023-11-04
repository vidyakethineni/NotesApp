package com.example.project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
/**
 * ViewModel for editing a specific note
 */
class EditNoteViewModel(noteId: Long, val dao: NoteDao) : ViewModel() {
    var note = MutableLiveData<Note>()
    val noteId : Long = noteId
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList
    /**
     * Initializes the ViewModel and retrieves the note from the database.
     */
    init {
        dao.get(noteId).observeForever { it ->
            if (it == null) {
                note.value = Note()
            } else {
                note.value = it
            }
        }
    }
    /**
     * Updates or inserts the note into the database and navigates back to the list view.
     */
    fun updateNote() {
        viewModelScope.launch {
            if(note.value?.noteId != 0L) {
                dao.update(note.value!!)
            } else {
                dao.insert(note.value!!)
            }
            _navigateToList.value = true
        }
    }
    /**
     * Deletes the note from the database and navigates back to the list view.
     */
    fun deleteNote() {
        viewModelScope.launch {
            dao.delete(note.value!!)
            _navigateToList.value = true
        }
    }
    /**
     * Clears the navigation state after the navigation has occurred.
     */
    fun onNavigatedToList() {
        _navigateToList.value = false
    }
    /**
     * Performs cleanup when the ViewModel is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
    }
}