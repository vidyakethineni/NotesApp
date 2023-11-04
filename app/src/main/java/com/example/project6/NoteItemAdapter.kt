package com.example.project6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project6.databinding.NoteItemBinding

/**
 * An adapter for managing Note items in a RecyclerView
 */
class NoteItemAdapter(val clickListener: (noteId: Long) -> Unit,
                      val deleteClickListener: (noteId: Long) -> Unit)
    : ListAdapter<Note, NoteItemAdapter.NoteItemViewHolder>(NoteDiffItemCallback()) {

    /**
     * Creates a new NoteItemViewHolder when needed
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NoteItemViewHolder = NoteItemViewHolder.inflateFrom(parent)
    /**
     * Binds data to the NoteItemViewHolder
     */
    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, deleteClickListener)
    }
    /**
     * ViewHolder for individual note items in the RecyclerView
     */
    class NoteItemViewHolder(val binding: NoteItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        /**
         * Inflates the view from the given parent ViewGroup
         */
        companion object {
            fun inflateFrom(parent: ViewGroup): NoteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return NoteItemViewHolder(binding)
            }
        }
        /**
         * Binds the Note data to the ViewHolder and sets click listeners
         */
        fun bind(item: Note, clickListener: (noteId: Long) -> Unit,
                 deleteClickListener: (noteId: Long) -> Unit) {
            binding.note = item
            binding.root.setOnClickListener { clickListener(item.noteId) }
            binding.deleteNoteButton.setOnClickListener { deleteClickListener(item.noteId) }
        }
    }
}