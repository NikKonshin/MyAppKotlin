package com.example.myappkotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

fun randomColor(): Color {
    val colors: Array<Color> = Color.values()
    val id = Random.nextInt((colors.size - 1) + 1)

    return colors[id]
}

object NotesRepositoryImpl : NotesRepository {

    private val notes: MutableList<Note> = mutableListOf()

    private val allNotes = MutableLiveData(getListForNotify())

    override fun observeNotes(): LiveData<List<Note>> {
        return allNotes
    }

    override fun addOrReplaceNote(newNote: Note) {
        notes.find { it.id == newNote.id }?.let {
            if (it == newNote) return

            notes.remove(it)
        }

        notes.add(newNote)

        allNotes.postValue(
            getListForNotify()
        )
    }

    private fun getListForNotify(): List<Note> = notes.toMutableList().also {
        it.reverse()
    }

}