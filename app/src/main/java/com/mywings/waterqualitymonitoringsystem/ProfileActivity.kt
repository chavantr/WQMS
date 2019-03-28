package com.mywings.waterqualitymonitoringsystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var progressDialogUtil: ProgressDialogUtil
    private var lid: Int = 0
    private var lname: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        progressDialogUtil = ProgressDialogUtil(this)

        lblLocation.setOnClickListener {
            val intent = Intent(this@ProfileActivity, SelectLocationActivity::class.java)
            startActivityForResult(intent, SELECT_LOCATION)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_LOCATION) {
                lblLocation.text = UserInfoHolder.getInstance().location.name
                lid = UserInfoHolder.getInstance().location.id
                lname = UserInfoHolder.getInstance().location.name
            }
        }
    }


    companion object {
        const val SELECT_LOCATION = 759
    }
}
