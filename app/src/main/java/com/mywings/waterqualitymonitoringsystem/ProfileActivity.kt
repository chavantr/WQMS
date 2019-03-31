package com.mywings.waterqualitymonitoringsystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.OnUpdateProfileListener
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import com.mywings.waterqualitymonitoringsystem.process.UpdateProfileAsync
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity(), OnUpdateProfileListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil
    private var lid: Int = 0
    private var lname: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        progressDialogUtil = ProgressDialogUtil(this)

        val user = UserInfoHolder.getInstance().user

        lid = user.lid
        lname = user.lname
        txtUserName.setText(user.username)
        txtPassword.setText(user.password)
        txtName.setText(user.name)
        txtEmail.setText(user.email)
        txtNumber.setText(user.number)
        lblLocation.text = user.lname

        btnSignUp.setOnClickListener {
            init()
        }

        lblLocation.setOnClickListener {
            val intent = Intent(this@ProfileActivity, SelectLocationActivity::class.java)
            startActivityForResult(intent, SELECT_LOCATION)
        }
    }

    private fun init() {
        progressDialogUtil.show()
        val updateProfileAsync = UpdateProfileAsync()
        val jRequest = JSONObject()
        val param = JSONObject()
        param.put("Name", txtName.text)
        param.put("Email", txtEmail.text)
        param.put("Username", txtUserName.text)
        param.put("Password", txtPassword.text)
        param.put("LId", lid)
        param.put("Number", txtNumber.text)
        param.put("LName", lname)
        param.put("Id", UserInfoHolder.getInstance().user.id)
        jRequest.put("request", param)
        updateProfileAsync.setOnUpdateProfileListener(this, jRequest)
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

    override fun onProfileUpdateSuccess(result: String?) {
        progressDialogUtil.hide()
        if (!result.isNullOrEmpty()) {
            Toast.makeText(this, "Profile updated.", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val SELECT_LOCATION = 759
    }
}
