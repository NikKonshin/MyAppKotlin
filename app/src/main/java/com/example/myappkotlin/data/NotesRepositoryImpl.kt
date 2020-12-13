package com.example.myappkotlin.data

import com.example.myappkotlin.data.db.FireStoreDataBaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

fun randomColor(): Color {
    val colors: Array<Color> = Color.values()
    val id = Random.nextInt((colors.size - 1) + 1)

    return colors[id]
}

class NotesRepositoryImpl(private val provider: FireStoreDataBaseProvider) : NotesRepository {

    override suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        provider.deleteNote(note)
    }

    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        provider.getCurrentUser()
    }

    override fun observeNotes(): Flow<List<Note>> {
        return provider.observeNotes()
    }

    override suspend fun addOrReplaceNote(newNote: Note) = withContext(Dispatchers.IO) {
        provider.addOrReplaceNote(newNote)
    }


}
