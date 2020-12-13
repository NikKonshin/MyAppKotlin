package com.example.myappkotlin.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesViewModelTest {
    private val notesRepositoryMockk = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<Result<Note>>()
    private lateinit var viewModel: NotesViewModel


    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


}