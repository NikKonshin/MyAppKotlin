package com.example.myappkotlin.presentation

import androidx.lifecycle.ViewModel
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepositoryImpl

class NoteViewModel(var note: Note?) : ViewModel() {

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    override fun onCleared() {
        super.onCleared()

        note?.let {
            NotesRepositoryImpl.addOrReplaceNote(it)
        }
    }

    private fun generateNote(): Note {
        return Note()
    }
}