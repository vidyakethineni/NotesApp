package com.example.project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    var TAG = "NotesViewModel"
    private var auth: FirebaseAuth

    private lateinit var notesCollection: CollectionReference
    private lateinit var notesDatabaseReference: DatabaseReference
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    var user: User = User()
    var verifyPassword = ""
    var noteId: String = ""
    var note = MutableLiveData<Note>()
    private val _notes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>>
        get() = _notes as LiveData<List<Note>>

    init {
        auth = Firebase.auth
        if (noteId.trim() == "") {
            note.value = Note()
        }
        _notes.value = mutableListOf<Note>()
    }

    private val _navigateToNote = MutableLiveData<String?>()
    val navigateToNote: LiveData<String?>
        get() = _navigateToNote

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?>
        get() = _errorHappened

    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    private val _navigateToSignUp = MutableLiveData<Boolean>(false)
    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    private val _navigateToSignIn = MutableLiveData<Boolean>(false)
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn



    private val _showDeleteConfirmationDialog = MutableLiveData<Pair<String, () -> Unit>>()
    val showDeleteConfirmationDialog: LiveData<Pair<String, () -> Unit>>
        get() = _showDeleteConfirmationDialog




//    fun initializeTheDatabaseReference() {
//
//        val database = Firebase.firestore
//        notesCollection = database.collection("notes")
//        notesCollection
//            .addSnapshotListener { dataSnapshot, e ->
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                var notesList: ArrayList<Note> = ArrayList()
//                for (noteSnapshot in dataSnapshot!!) {
//                    var note = noteSnapshot.toObject<Note>()
//                    note?.noteId = noteSnapshot.id
//                    notesList.add(note!!)
//                }
//                _notes.value = notesList
//
//            }
//
//    }
fun initializeTheDatabaseReference() {
    // Get the current user's UID
    val uid = auth.currentUser?.uid ?: ""

    // Reference to the notes location in the Firebase Realtime Database
    notesDatabaseReference = firebaseDatabase.getReference("notes").child(uid)

    // Add a ValueEventListener to update notes when they change in the database
    notesDatabaseReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val notesList: ArrayList<Note> = ArrayList()
            for (noteSnapshot in snapshot.children) {
                val note = noteSnapshot.getValue(Note::class.java)
                note?.noteId = noteSnapshot.key.toString()
                note?.let { notesList.add(it) }
            }
            _notes.value = notesList
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    })
}

    fun getAll(): LiveData<List<Note>> {
        return notes
    }
//    fun updateNote() {
//        if (noteId.trim() == "") {
//            note.value?.userId = auth.currentUser!!.uid
//            notesCollection.add(note.value!!)
//                .addOnSuccessListener { documentReference ->
//                    noteId = documentReference.id
//                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error adding document", e)
//                }
//        } else {
//            notesCollection.document(noteId).set(note.value!!)
//        }
//        _navigateToList.value = true
//    }

    fun updateNote() {
        if (noteId.trim() == "") {
            val newNoteReference = notesDatabaseReference.push()
            noteId  = newNoteReference.key.toString()
            note.value?.copy(
                userId = auth.currentUser!!.uid,
                noteId = noteId
            )
            newNoteReference.setValue(note.value)
                .addOnSuccessListener {
                    Log.d(TAG, "Note added with ID: $noteId")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding note", e)
                }
        } else {
            notesDatabaseReference.child(noteId).setValue(note.value)
                .addOnSuccessListener {
                    Log.d(TAG, "Note updated with ID: $noteId")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating note", e)
                }
        }
        _navigateToList.value = true
    }

    /**
     * Coroutine function to delete a note from the database by its ID
     */
//    fun deleteNote(noteId: String) {
//        notesCollection.document(noteId)
//            .delete()
//            .addOnSuccessListener { Log.d(TAG, "Note successfully deleted!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Error deleting note ${note.value?.noteName}", e) }
//    }


    fun deleteNote(noteId: String) {
            notesDatabaseReference.child(noteId).removeValue()
    }

    /**
     * Method triggered when a note is clicked for navigation purposes
     */
    fun onNoteClicked(selectedNote: Note) {
        _navigateToNote.value = selectedNote.noteId
        noteId = selectedNote.noteId
        note.value = selectedNote
    }

    fun onNewNoteClicked() {
        _navigateToNote.value = ""
        noteId = ""
        note.value = Note()
    }
    /**
     * Method invoked after note navigation is completed
     */
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
    fun onNavigatedToList() {
        _navigateToList.value = false
    }

    fun navigateToSignUp() {
        _navigateToSignUp.value = true
    }

    fun onNavigatedToSignUp() {
        _navigateToSignUp.value = false
    }
    fun navigateToSignIn() {
        _navigateToSignIn.value = true
    }
    fun onNavigatedToSignIn() {
        _navigateToSignIn.value = false
    }
    fun signIn() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {

                _navigateToList.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }
    fun signUp() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        if (user.password != verifyPassword) {
            _errorHappened.value = "Password and verify do not match."
            return
        }
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                _navigateToSignIn.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _navigateToSignIn.value = true
    }
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}