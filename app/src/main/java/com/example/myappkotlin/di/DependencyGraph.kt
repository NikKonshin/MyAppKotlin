package com.example.myappkotlin.di

import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepository
import com.example.myappkotlin.data.NotesRepositoryImpl
import com.example.myappkotlin.data.db.DatabaseProvider
import com.example.myappkotlin.data.db.FireStoreDataBaseProvider
import com.example.myappkotlin.presentation.NoteViewModel
import com.example.myappkotlin.presentation.NotesViewModel
import com.example.myappkotlin.presentation.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object DependencyGraph {
    private val repositoryModule by lazy {
        module {
            single { NotesRepositoryImpl(get()) } bind NotesRepository::class
            single { FireStoreDataBaseProvider() } bind DatabaseProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { NotesViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note?) -> NoteViewModel(get(), note) }
        }
    }
    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}