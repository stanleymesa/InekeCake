package com.example.inekecake.model

data class ResponseModel (
    var message: String = "",
    var data: ArrayList<DataModel> = arrayListOf()
)