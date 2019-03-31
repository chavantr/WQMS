package com.mywings.waterqualitymonitoringsystem

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.OnRegistrationListener
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import com.mywings.waterqualitymonitoringsystem.process.RegistrationAsync
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity(), OnRegistrationListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil
    private var lid: Int = 0
    private var lname: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        progressDialogUtil = ProgressDialogUtil(this)

        lblLocation.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, SelectLocationActivity::class.java)
            startActivityForResult(intent, SELECT_LOCATION)
        }

        btnSignUp.setOnClickListener {
            if (validate()) {
                init()
            } else {
                Toast.makeText(this, "Fields required", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validate(): Boolean = txtName.text!!.isNotEmpty()
            && txtEmail.text!!.isNotEmpty()
            && txtUserName.text!!.isNotEmpty()
            && txtPassword.text!!.isNotEmpty()
            && txtNumber.text!!.isNotEmpty()
            && lid > 0 && lname.isNotEmpty()

    private fun init() {
        progressDialogUtil.show()
        val registrationAsync = RegistrationAsync()
        val jRequest = JSONObject()
        val param = JSONObject()
        param.put("Name", txtName.text)
        param.put("Email", txtEmail.text)
        param.put("Username", txtUserName.text)
        param.put("Password", txtPassword.text)
        param.put("LId", lid)
        param.put("Number", txtNumber.text)
        param.put("LName", lname)
        jRequest.put("request", param)
        registrationAsync.setOnRegistrationListener(this, jRequest)
    }


    override fun onRegistration(result: String?) {
        try {
            progressDialogUtil.hide()
            if (result!!.isNotEmpty()) {
                Toast.makeText(this, "Successfully registered.", Toast.LENGTH_LONG).show()
                finish()
            }
        } catch (e: Exception) {
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
