package com.example.myappkotlin.data

import com.example.myappkotlin.model.User
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(note: Note)


}