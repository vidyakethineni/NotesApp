package com.example.project6

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

/**
 * A DialogFragment to confirm deletion, providing options to confirm or cancel the deletion process.
 */
class ConfirmDeleteDialogFragment(val noteId : Long,val clickListener: (noteId: Long) -> Unit) : DialogFragment() {
    val TAG = "ConfirmDeleteDialogFragment"
    /**
     * The interface to handle click events within the dialog for confirmation.
     */
    interface myClickListener {
        fun yesPressed()
    }

    /**
     * Creates and configures the delete confirmation dialog.
     */
    var listener: myClickListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete?")
            .setPositiveButton("Yes") { _,_ -> clickListener(noteId)}
            .setNegativeButton("No") { _,_ -> }

            .create()

    companion object {
        const val TAG = "ConfirmDeleteDialogFragment"
    }
    /**
     * Attaches the confirmation dialog fragment to the context and sets up a listener.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as myClickListener
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }




}