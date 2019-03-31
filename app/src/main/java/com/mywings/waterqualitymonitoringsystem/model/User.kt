package com.mywings.waterqualitymonitoringsystem.model

data class User(
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var lid: Int = 0,
    var number: String = "",
    var lname: String = ""
)