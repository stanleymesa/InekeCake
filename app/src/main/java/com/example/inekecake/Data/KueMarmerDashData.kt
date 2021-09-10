package com.example.inekecake.Data

import com.example.inekecake.Model.KueMarmerModel
import com.example.inekecake.R

class KueMarmerDashData {
    private val images = arrayListOf(
        R.drawable.original,
        R.drawable.keju,
        R.drawable.almond,
        R.drawable.oreo,
        R.drawable.meises
    )

    private val name = arrayListOf(
        "Kue Marmer\nOriginal",
        "Kue Marmer\nKeju",
        "Kue Marmer\nAlmond",
        "Kue Marmer\nOreo",
        "Kue Marmer\nMeises",
    )

    val getList: ArrayList<KueMarmerModel>
        get() {
            val list = arrayListOf<KueMarmerModel>()
            for (i in images.indices) {
                val model = KueMarmerModel(images[i], name[i])
                list.add(model)
            }
            return list
        }
}