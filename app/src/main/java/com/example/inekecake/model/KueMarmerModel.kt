package com.example.inekecake.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KueMarmerModel(
    val id: String = "",
    val url: String = "",
    val nama: String = "",
    val harga: String = "",
    val is_available: Boolean = true,
): Parcelable
