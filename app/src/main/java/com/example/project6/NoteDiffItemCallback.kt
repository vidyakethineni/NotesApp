package com.example.project6

import androidx.recyclerview.widget.DiffUtil

/**
 * Callback for calculating the difference between two non-null Note items in a list
 */
class NoteDiffItemCallback : DiffUtil.ItemCallback<Note>()  {
    /**
     * Checks whether the items represented by the old and new Notes are the same
     */
    override fun areItemsTheSame(oldItem: Note, newItem: Note)
            = (oldItem.noteId == newItem.noteId)
    /**
     * Checks whether the contents of the old and new Notes are the same
     */
    override fun areContentsTheSame(oldItem: Note, newItem: Note) = (oldItem == newItem)
}