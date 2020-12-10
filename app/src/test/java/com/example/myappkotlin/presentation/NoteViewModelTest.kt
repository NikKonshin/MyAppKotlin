package com.example.myappkotlin.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepository
import com.example.myappkotlin.model.User
import io.mockk.*
import junit.framework.TestCase
import org.junit.*
import org.junit.rules.TestRule

class NoteViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: NoteViewModel

    private var _resultLiveData = MutableLiveData(
        Result.success(
            Note()
        )
    )
    private val resultLiveData: LiveData<Result<Note>> get() = _resultLiveData

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.getCurrentUser() } returns User("jon", "mail.ru")
        every { notesRepositoryMock.deleteNote(any()) }
        every { notesRepositoryMock.addOrReplaceNote(Note()) } returns resultLiveData

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Success LiveData contains nothing after save`() {
        val currentNote = Note(title = "Hello")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()
        Assert.assertTrue(viewModel.showError().value == null)
    }

    @Test
    fun `Error LiveData contains nothing after save`() {
        every { notesRepositoryMock.addOrReplaceNote(any()) } returns MutableLiveData(
            Result.failure(
                IllegalAccessError()
            )
        )
        val currentNote = Note(title = "Hello")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()
        Assert.assertTrue(viewModel.showError().value == null)
    }

    @Test
    fun `view model title change`() {
        val currentNote = Note()
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.updateTitle("123")

        Assert.assertEquals("123", viewModel.note?.title)


    }

    @Test
    fun `test correct save Note `() {
        viewModel = NoteViewModel(notesRepositoryMock, null)

        viewModel.updateNote("Hi")
        viewModel.saveNote()
        verify(exactly = 1) {
            notesRepositoryMock.addOrReplaceNote(match { it.note == "Hi" })
        }

    }

}