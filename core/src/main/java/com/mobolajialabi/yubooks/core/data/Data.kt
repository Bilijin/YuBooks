package com.mobolajialabi.yubooks.core.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  User(
    val id: String = "",
    val email: String = "",
    val username : String = "",
    val phoneNo : String = ""
) : Parcelable

data class Book(var name : String = "",
           var id: String = "",
           var price : Int = 0,
           var rating : Int = 0,
           var image : String = ""
)