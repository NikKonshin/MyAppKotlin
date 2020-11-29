package com.example.myappkotlin.data.db

import androidx.lifecycle.LiveData
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.model.User

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
}