package com.example.inekecake.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseModel(
    var firstname: String = "",
    var lastname: String = "",
    var email: String = "",
    var noHp: String = "",
    var password: String = ""
) : Parcelable