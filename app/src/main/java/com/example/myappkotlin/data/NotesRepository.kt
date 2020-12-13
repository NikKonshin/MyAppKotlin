package com.example.myappkotlin.data

import androidx.lifecycle.LiveData
import com.example.myappkotlin.model.User

interface NotesRepository {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
}