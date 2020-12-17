package com.mobolajialabi.yubooks.cart

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseHelper {

    private val db = Firebase.firestore
    fun retrieveBooks() : ArrayList<Book> {
        val books = ArrayList<Book>()
        db.collection("books")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    books.add(Book(document.getString("name").toString(),
                        document.getLong("price")!!.toInt(),
                    document.getLong("rating")!!.toInt()))
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return books
    }

    fun retrieveCart() : ArrayList<Book> {
        val books = ArrayList<Book>()
        db.collection("users").document()
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    books.add(Book(document.getString("name").toString(),
//                        document.getLong("price")!!.toInt(),
//                        document.getLong("rating")!!.toInt()))
//                }
//
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
    }
}