package com.example.inekecake.data

import com.example.inekecake.model.DesignDashModel
import com.example.inekecake.R

class DesignDashData {
    private val images = arrayListOf(
        R.drawable.designing,
        R.drawable.schedule,
        R.drawable.payment,
        R.drawable.delivery
    )

    private val steps = arrayListOf(
        "Step 1",
        "Step 2",
        "Step 3",
        "Step 4"
    )

    private val desc = arrayListOf(
        "Hias cake sesuka anda",
        "Atur jadwal pengiriman atau pengambilan",
        "Bayar dengan OVO/GOPAY/M-Banking",
        "Cake datang ke rumah anda"
    )

    val getList: ArrayList<DesignDashModel>
        get() {
            val list = arrayListOf<DesignDashModel>()
            for (i in images.indices) {
                val model = DesignDashModel(images[i], steps[i], desc[i])
                list.add(model)
            }
            return list
        }
}