package com.example.myappkotlin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.myappkotlin.R
import com.example.myappkotlin.data.errors.NoAuthException
import com.example.myappkotlin.data.notesRepository
import com.example.myappkotlin.presentation.SplashViewModel
import com.example.myappkotlin.presentation.SplashViewState
import com.firebase.ui.auth.AuthUI

private const val RC_SIGN_IN = 458
private const val TAG = "VJQ"
class SplashActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SplashViewModel(notesRepository) as T
            }
        }).get(
            SplashViewModel::class.java
        )
    }

    private val layoutRes: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG, "Сработало  onCreate")
        setContentView(layoutRes)

        viewModel.observeViewState().observe(this){
            when(it){
                is SplashViewState.Error -> renderError(it.error)
                SplashViewState.Auth -> renderData()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLoginActivity()
        Log.d(TAG, "Сработало  onResume()")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Сработало  onActivityResult")

        when{
            requestCode != RC_SIGN_IN -> return
            requestCode != Activity.RESULT_OK -> finish()
            requestCode == Activity.RESULT_OK -> renderData()
        }
    }

    private fun startMainActivity(){
        Log.d(TAG, "Сработало  startMainActivity()")
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    private fun renderData() {
        Log.d(TAG, "Сработало  renderData()")
        startMainActivity()
    }

    private fun renderError(error: Throwable) {
        when(error) {
            is NoAuthException -> startLoginActivity()
            else -> error.message?.let {Toast.makeText(this, it, Toast.LENGTH_LONG).show()  }
        }
    }

    private fun startLoginActivity() {
        Log.d(TAG, "Сработало  startLoginActivity()")

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_launcher_foreground)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN,
        )
    }
}