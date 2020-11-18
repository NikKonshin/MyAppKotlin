package com.example.myappkotlin.data

interface NotesRepository {
  fun getNotes() : List<Note>
}