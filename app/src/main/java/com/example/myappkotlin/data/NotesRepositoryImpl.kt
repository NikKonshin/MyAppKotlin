package com.example.myappkotlin.data

import androidx.lifecycle.LiveData
import com.example.myappkotlin.data.db.FireStoreDataBaseProvider
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

fun randomColor(): Color {
    val colors: Array<Color> = Color.values()
    val id = Random.nextInt((colors.size - 1) + 1)

    return colors[id]
}

class NotesRepositoryImpl(val provider: FireStoreDataBaseProvider) : NotesRepository {
    override fun observeNotes(): LiveData<List<Note>> {
        return provider.observeNotes()
    }

    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        return provider.addOrReplaceNote(newNote)
    }
}
val notesRepository: NotesRepository by lazy { NotesRepositoryImpl(FireStoreDataBaseProvider()) }