package com.example.inekecake.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseModel(
    var firstname: String = "",
    var lastname: String = "",
    var email: String = "",
    var noHp: String = "",
    var password: String = "",
    var alamat: String = "",
    var kota: String = "",
    var kodepos: String = "",
    var tgl_register: String = ""
) : Parcelable