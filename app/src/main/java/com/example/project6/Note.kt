package com.example.project6;

import com.google.firebase.firestore.Exclude

/**
 * Entity class representing a Note in the database
 */
data class Note(
    @get:Exclude
    var noteId: String = "",
    var noteName: String = "",
    var noteDescription: String = "",
    var userId: String = ""
)