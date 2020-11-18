package com.example.myappkotlin.data

object NotesRepositoryImpl : NotesRepository {

    private val notes: List<Note> = listOf(
        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),
        Note(
            title = "Моя заметка",
            note = "Привет"
        ),

        Note(
            title = "Моя заметка",
            note = "Привет"
        ),


        )


    override fun getNotes(): List<Note> {
        return notes
    }

}