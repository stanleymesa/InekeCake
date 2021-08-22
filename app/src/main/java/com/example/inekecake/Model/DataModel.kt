package com.example.inekecake.Model

import android.os.Parcelable

data class DataModel(
    var id: Int = 0,
    var nama: String = "",
    var noHp: String = "",
    var alamat: String = "",
    var cake: String = "",
    var harga: String = "",
    var tglPesan: String = "",
    var tglKirim: String = ""
)