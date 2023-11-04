package com.example.project6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.project6.databinding.FragmentNotesBinding
import kotlinx.coroutines.launch

/**
 * A fragment to display a list of notes and handle interactions with them
 */
class NotesFragment : Fragment() {
    // Tag for logging purposes
    val TAG = "NotesFragment"
    // View binding for this fragment
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * Handles the action when a note is clicked
         */
        fun noteClicked (noteId : Long) {
            viewModel.onNoteClicked(noteId)
        }

        /**
         * Action performed when 'yes' is pressed in the confirmation dialog for a note is being deleted
         */
        fun yesPressed(noteId : Long){
            Log.d(TAG,"in yesPressed(): noteId = $noteId")
            binding.viewModel?.deleteNote(noteId)
        }

        /**
         * Triggered when attempting to delete a note. Shows a confirmation dialog
         */
        fun deleteClicked (noteId : Long) {
            ConfirmDeleteDialogFragment(noteId,::yesPressed).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }

        // Creates an adapter with specified click listeners
        val adapter = NoteItemAdapter(::noteClicked,::deleteClicked)

        // Sets the adapter for the notes list view
        binding.notesList.adapter = adapter

        // Observes changes in the list of notes and updates the adapter accordingly
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        })

        // Observes navigation to a specific note for editing
        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                val action = NotesFragmentDirections
                    .actionNotesFragmentToEditNoteFragment(noteId)
                this.findNavController().navigate(action)
                viewModel.onNoteNavigated()
            }
        })


        return view
    }
    /**
     * Called when the view previously created by onCreateView() has been destroyed
     */
    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}