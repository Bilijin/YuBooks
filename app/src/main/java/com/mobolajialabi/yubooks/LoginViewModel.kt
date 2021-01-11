package com.mobolajialabi.yubooks

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel(var app : Application, val listen : SignInClient) : AndroidViewModel(app) {

    private val auth = Firebase.auth
    private val _boo = MutableLiveData<Boolean>(false)
    val boo : LiveData<Boolean> = _boo

    private val _reed = MutableLiveData<Boolean>(false)
    val reed : LiveData<Boolean> = _reed

    // Build a GoogleSignInClient with the options specified by gso.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(app, gso)

    fun handleSignIn(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener {
                task ->
            _boo.value = task.isSuccessful
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                _reed.value = task.isSuccessful
            }
    }

    fun handleGoogleSign() {
        listen.signInStarted(mGoogleSignInClient)
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val app : Application, private val listener : SignInClient) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(app, listener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}