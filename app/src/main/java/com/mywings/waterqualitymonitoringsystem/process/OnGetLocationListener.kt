package com.mywings.waterqualitymonitoringsystem.process

import com.mywings.waterqualitymonitoringsystem.model.Location

interface OnGetLocationListener {
    fun onLocationSuccess(result: List<Location>?)
}