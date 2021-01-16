package com.mobolajialabi.yubooks

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel(var app : Application, val listen : SignInClient) : AndroidViewModel(app) {

    private val auth = Firebase.auth
    private val _bee = MutableLiveData<Boolean>(false)
    val bee : LiveData<Boolean> = _bee

    private val _bool = MutableLiveData<Boolean>(false)
    val bool : LiveData<Boolean> = _bool

    // Build a GoogleSignInClient with the options specified by gso.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(app, gso)

    fun register(email : String, password : String, username : String, phone : String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val dbHelper = DatabaseHelper()
                auth.currentUser?.uid?.let { it1 ->
                    dbHelper.createUser(it1, email, username, phone)
                }
            }

            _bee.value = task.isSuccessful
        }
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

            return LoginViewModel(app, listener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}