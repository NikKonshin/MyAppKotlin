package com.example.myappkotlin

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class GetData: LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getData() = println("Work db")
}