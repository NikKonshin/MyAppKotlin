package com.example.myappkotlin.data.db

import androidx.lifecycle.LiveData
import com.example.myappkotlin.data.Note

interface DatabaseProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
}