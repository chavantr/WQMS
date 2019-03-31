package com.mywings.waterqualitymonitoringsystem.process

import com.mywings.waterqualitymonitoringsystem.model.Sensor

interface OnGetSensorListener {
    fun onSensorSuccess(result: List<Sensor>?)
}