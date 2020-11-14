package com.example.myappkotlin

import androidx.lifecycle.LiveData

class MyLiveData: LiveData<String>() {

    fun setValueToMyLiveData(string: String){
        value = string
    }

    override fun onActive() {
        super.onActive()

    }

    override fun onInactive() {
        super.onInactive()
    }
}