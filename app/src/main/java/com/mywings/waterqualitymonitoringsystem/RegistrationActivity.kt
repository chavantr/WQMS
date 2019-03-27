package com.mywings.waterqualitymonitoringsystem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        progressDialogUtil = ProgressDialogUtil(this)
        lblLocation.setOnClickListener {

        }
    }


}
