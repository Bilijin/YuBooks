package com.mobolajialabi.yubooks

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DatabaseHelper {

    private val db = Firebase.firestore

    val auth = Firebase.auth

    fun forgotPassword(email: String): Boolean {
        var status = false

        auth.sendPasswordResetEmail(email).addOnCompleteListener {
             status = it.isSuccessful
        }
        return status

    }

    fun handleSignIn(email : String, password : String) : Task<AuthResult> {
        var status : = false
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener {
                task ->
           status = task.isSuccessful
        }
        return status
    }

    fun createUser(email: String, userName: String, phoneNo: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password)

        val user = User(auth.uid!!, email, userName, phoneNo)

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

}