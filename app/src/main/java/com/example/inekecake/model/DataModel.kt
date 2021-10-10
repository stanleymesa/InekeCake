package com.example.inekecake.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    var id: Int = 0,
    var nama: String = "",
    var noHp: String = "",
    var alamat: String = "",
    var cake: String = "",
    var harga: String = "",
    var tglPesan: String = "",
    var tglKirim: String = ""
) : Parcelable