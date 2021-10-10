package com.example.inekecake.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartModel(
    var id: String = "",
    var qty: String = "0"
): Parcelable
