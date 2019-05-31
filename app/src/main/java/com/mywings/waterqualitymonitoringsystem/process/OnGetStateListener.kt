package com.mywings.waterqualitymonitoringsystem.process

import com.mywings.waterqualitymonitoringsystem.model.States

interface OnGetStateListener {
    fun onStateSuccess(state: States?)
}