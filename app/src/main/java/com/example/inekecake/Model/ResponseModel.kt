package com.example.inekecake.Model

data class ResponseModel (
    var message: String = "",
    var data: ArrayList<DataModel> = arrayListOf()
)