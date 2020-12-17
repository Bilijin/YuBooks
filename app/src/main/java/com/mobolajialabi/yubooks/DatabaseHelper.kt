package com.mobolajialabi.yubooks

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class DatabaseHelper {

    private val db = Firebase.firestore

    fun createUser( id : String, email : String, userName: String, phoneNo : String) {

        val user = User( id, email, userName, phoneNo)

// Add a new document with a generated ID
        db.collection("users").document(userName.decapitalize(Locale.ROOT))
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ${userName.decapitalize(Locale.ROOT)}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

}