package com.example.myappkotlin

import androidx.multidex.MultiDexApplication
import com.example.myappkotlin.di.DependencyGraph
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NotesApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NotesApp)
            modules(DependencyGraph.modules)
        }
    }
}