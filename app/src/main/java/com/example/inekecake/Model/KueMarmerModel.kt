package com.example.inekecake.Model

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.parcelize.Parcelize

@Parcelize
data class KueMarmerModel(
    val url: String = "",
    val nama: String = "",
    val harga: String = ""
): Parcelable
