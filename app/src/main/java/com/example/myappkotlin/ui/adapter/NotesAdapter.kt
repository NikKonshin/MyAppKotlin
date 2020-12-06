package com.example.myappkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.mapToColor
import com.example.myappkotlin.databinding.ItemNoteBinding

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}

class NotesAdapter(val noteHandler: (Note) -> Unit) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        itemView: ViewGroup, private val binding: ItemNoteBinding = ItemNoteBinding.inflate(
            LayoutInflater.from(itemView.context), itemView, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentNote: Note

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        fun bind(note: Note) {
            currentNote = note
            with(binding) {
                title.text = note.title
                body.text = note.note
                root.setBackgroundColor(note.color.mapToColor(itemView.context))
                root.setOnClickListener(clickListener)
            }
        }
    }
}