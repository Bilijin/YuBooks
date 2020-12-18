package com.mobolajialabi.yubooks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  User(
    val id: String = "",
    val email: String = "",
    val username : String = "",
    val phoneNo : String = ""
) : Parcelable