package com.mobolajialabi.yubooks.auth.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.DatabaseHelper
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.util.SignInClient

class RegisterViewModel(var app : Application, val listen : SignInClient) : AndroidViewModel(app) {

    private val auth = Firebase.auth
    private val _isRegisterSuccessful = MutableLiveData<Boolean>(false)
    val isRegisterSuccessful : LiveData<Boolean> = _isRegisterSuccessful

    private val _bool = MutableLiveData<Boolean>(false)
    val bool : LiveData<Boolean> = _bool

    // Build a GoogleSignInClient with the options specified by gso.
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(app, gso)

    fun register(email : String, password : String, username : String, phone : String) {
               DatabaseHelper.createUser(email,username,phone,password)

    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                _bool.value = task.isSuccessful
            }
    }

    fun handleGoogleSign() {
        listen.signInStarted(mGoogleSignInClient)
    }


}

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(private val app : Application, private val listener : SignInClient) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(
                app,
                listener
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}