package com.example.project6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * Factory for creating instances of the EditNoteViewModel.
 */
class EditNoteViewModelFactory(private val noteId: Long,
                               private val dao: NoteDao)
    : ViewModelProvider.Factory {
    /**
     * Creates an instance of the specified ViewModel class.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(noteId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}