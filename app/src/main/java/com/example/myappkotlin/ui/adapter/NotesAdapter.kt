package com.example.myappkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myappkotlin.R
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.mapToColor
import kotlinx.android.synthetic.main.item_note.view.*

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

    inner class NoteViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(itemView.context)
            .inflate(R.layout.item_note, itemView, false)) {

        private lateinit var currentNote: Note

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        fun bind(note: Note) {
            currentNote = note
            with(itemView) {
                title.text = note.title
                body.text = note.note
                setBackgroundColor(note.color.mapToColor(itemView.context))
                setOnClickListener(clickListener)
            }
        }
    }


}