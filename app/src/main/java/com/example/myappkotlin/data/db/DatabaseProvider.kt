package com.example.myappkotlin.data.db

import com.example.myappkotlin.data.Note
import com.example.myappkotlin.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(note: Note)
}