package com.mobolajialabi.yubooks.core.data

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.core.Helpers.showMessage
import com.mobolajialabi.yubooks.core.R

import timber.log.Timber

object DatabaseHelper {

    private val db = Firebase.firestore
    val auth = Firebase.auth

    private val _googleSignInSuccessful = MutableLiveData<Boolean>(false)
    val googleSignInSuccessful: LiveData<Boolean> = _googleSignInSuccessful

    private val _isSignInSuccessful = MutableLiveData<Boolean>(false)
    val isSignInSuccessful: LiveData<Boolean> = _isSignInSuccessful

    private val _isRegisterSuccessful = MutableLiveData<Boolean>(false)
    val isRegisterSuccessful: LiveData<Boolean> = _isRegisterSuccessful

    private val _hasEmailBeenSent = MutableLiveData<Boolean>(false)
    val hasEmailBeenSent: LiveData<Boolean> = _hasEmailBeenSent

    private val _booksList = MutableLiveData<List<Book>>()
    val booksList: LiveData<List<Book>> = _booksList

    fun forgotPassword(email: String, view: View) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                _hasEmailBeenSent.value = true
                showMessage("Please Check Your Email for a reset link", view)
            } else {
                _hasEmailBeenSent.value = false
                it.exception?.localizedMessage?.let { it1 -> showMessage(it1, view) }
            }
        }

    }

    fun handleGoogleSignIn(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val signin = GoogleSignIn.getClient(context, gso)
        return signin
    }

    fun firebaseAuthWithGoogle(idToken: String, view: View) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage("Welcome to YuBooks", view)
                    _googleSignInSuccessful.value = task.isSuccessful
                } else {
                    task.exception?.localizedMessage?.let { showMessage(it, view) }
                }
            }
    }

    fun handleSignIn(view: View, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _isSignInSuccessful.value = true
                showMessage("Sign in Successful", view)
            } else {
                _isSignInSuccessful.value = false
                task.exception?.localizedMessage?.let { showMessage(it, view) }
            }
        }

    }

    fun createUser(view: View, email: String, userName: String, phoneNo: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = User(auth.uid!!, email, userName, phoneNo)

                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _isRegisterSuccessful.value = true
                            showMessage("Welcome to YuBooks", view)
                        } else {
                            _isRegisterSuccessful.value = false
                            task.exception?.localizedMessage?.let { errorMessage ->
                                showMessage(
                                    errorMessage,
                                    view
                                )
                            }
                        }
                    }

            }
        }
    }

    fun retrieveBooks() {
        val books = ArrayList<Book>()
        db.collection("books")
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    for (document in task.result!!) {
                        books.add(
                            Book(
                                document.getString("name").toString(),
                                document.getLong("price")!!.toString(),
                                document.getLong("rating")!!.toInt()
                            )
                        )
                    }
                    _booksList.value = books
                    Timber.d("bo}oksList %s", _booksList.value)

                } else {
                    Timber.d(task.exception, "Error getting documents.")
                }
            }
    }
}