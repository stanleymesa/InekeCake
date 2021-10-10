package com.example.inekecake.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DesignDashModel(
    var image: Int,
    var step: String,
    var desc: String
): Parcelable
