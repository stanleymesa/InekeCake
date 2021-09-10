package com.example.inekecake.Model

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.parcelize.Parcelize

@Parcelize
data class KueMarmerModel(
    val image: Int,
    val name: String
): Parcelable
