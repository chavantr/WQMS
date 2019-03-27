package com.mywings.waterqualitymonitoringsystem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mywings.waterqualitymonitoringsystem.model.Location
import com.mywings.waterqualitymonitoringsystem.process.GetLocationAsync
import com.mywings.waterqualitymonitoringsystem.process.HttpConnectionUtil
import com.mywings.waterqualitymonitoringsystem.process.OnGetLocationListener
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import generalknowledge.mywings.com.smartdustbinsystem.joint.JointAdapter
import generalknowledge.mywings.com.smartdustbinsystem.joint.OnLocationQListener
import kotlinx.android.synthetic.main.activity_select_location.*

class SelectLocationActivity : AppCompatActivity(), OnGetLocationListener, OnLocationQListener {


    private lateinit var httpConnectionUtil: HttpConnectionUtil
    private lateinit var jointAdapter: JointAdapter
    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)
        httpConnectionUtil = HttpConnectionUtil()
        lstLocation.layoutManager = LinearLayoutManager(this)
        progressDialogUtil = ProgressDialogUtil(this)
    }

    private fun init() {
        progressDialogUtil.show()
        val getLocationAsync = GetLocationAsync()
        getLocationAsync.setOnLocationListener(this)
    }

    override fun onLocationSuccess(result: List<Location>?) {
        progressDialogUtil.hide()
        if (result!!.isNotEmpty()) {
            jointAdapter = JointAdapter(result)
            jointAdapter.setOnVehicleSelectedListener(this)
        }
    }

    override fun onVehicleSelected(vehicle: Location) {

    }
}
