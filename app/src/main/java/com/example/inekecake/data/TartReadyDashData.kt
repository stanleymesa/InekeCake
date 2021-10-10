package com.example.inekecake.data

import com.example.inekecake.model.TartReadyDashModel
import com.example.inekecake.R

class TartReadyDashData {
    private val images = arrayListOf(
        R.drawable.tr1,
        R.drawable.tr2,
        R.drawable.tr3,
        R.drawable.tr4,
        R.drawable.tr5,
        R.drawable.tr6,
        R.drawable.tr7
    )

    val getList: ArrayList<TartReadyDashModel>
        get() {
            val list = arrayListOf<TartReadyDashModel>()
            for (i in images.indices) {
                val model = TartReadyDashModel(images[i])
                list.add(model)
            }
            return list
        }

}