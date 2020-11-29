package com.example.myappkotlin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myappkotlin.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .addToBackStack("note")
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .addToBackStack("")
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    companion object{
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
