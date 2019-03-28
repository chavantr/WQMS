package com.mywings.waterqualitymonitoringsystem.process

import com.mywings.waterqualitymonitoringsystem.model.User

interface OnLoginListener {
    fun onLoginSuccess(user: User?)
}