package com.mywings.waterqualitymonitoringsystem

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mywings.waterqualitymonitoringsystem.model.Location
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.GetLocationAsync
import com.mywings.waterqualitymonitoringsystem.process.OnGetLocationListener
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import generalknowledge.mywings.com.smartdustbinsystem.joint.JointAdapter
import generalknowledge.mywings.com.smartdustbinsystem.joint.OnLocationQListener
import kotlinx.android.synthetic.main.activity_select_location.*

class SelectLocationActivity : AppCompatActivity(), OnGetLocationListener, OnLocationQListener {

    private lateinit var jointAdapter: JointAdapter
    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)
        lstLocation.layoutManager = LinearLayoutManager(this)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
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
            lstLocation.adapter = jointAdapter
        }
    }

    override fun onVehicleSelected(location: Location) {
        UserInfoHolder.getInstance().location = location
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }
}
