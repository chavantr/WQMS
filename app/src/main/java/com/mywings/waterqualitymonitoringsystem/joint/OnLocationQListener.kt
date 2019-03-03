package generalknowledge.mywings.com.smartdustbinsystem.joint

import com.mywings.waterqualitymonitoringsystem.model.Location

interface OnLocationQListener {
    fun onVehicleSelected(vehicle: Location)
}