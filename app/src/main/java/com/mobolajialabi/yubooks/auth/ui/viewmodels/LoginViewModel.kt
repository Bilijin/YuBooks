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

class LoginViewModel(var app : Application, private val listen : SignInClient) : AndroidViewModel(app) {

    private val auth = Firebase.auth
    private val _boo = MutableLiveData<Boolean>(false)
    val boo : LiveData<Boolean> = _boo

    private val _googleSinInSuccessful = MutableLiveData<Boolean>(false)
    val googleSiginSuccessful : LiveData<Boolean> = _googleSinInSuccessful
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    // Build a GoogleSignInClient with the options specified by gso.
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(app, gso)

    fun handleSignIn(email : String, password : String) {
//        var status :Boolean = false
        DatabaseHelper.auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener {
                task ->
            if (task.isSuccessful){
                _boo.value = task.isSuccessful
            }else{
                _errorMessage.value = task.exception?.localizedMessage
            }

        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    _googleSinInSuccessful.value = task.isSuccessful
                }else{
                    _errorMessage.value = task.exception?.localizedMessage
                }
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

            return LoginViewModel(
                app,
                listener
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}