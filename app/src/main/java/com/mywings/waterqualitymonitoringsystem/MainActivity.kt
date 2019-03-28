package com.mywings.waterqualitymonitoringsystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.waterqualitymonitoringsystem.model.User
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.LoginAsync
import com.mywings.waterqualitymonitoringsystem.process.OnLoginListener
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), OnLoginListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialogUtil = ProgressDialogUtil(this)
        btnSignIn.setOnClickListener {
            if (validate()) {
                init()
            } else {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_LONG).show()
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validate(): Boolean = txtUserName.text!!.isNotEmpty() && txtPassword.text!!.isNotEmpty()

    private fun init() {
        progressDialogUtil.show()
        val loginAsync = LoginAsync()
        val jRequest = JSONObject()
        val param = JSONObject()
        jRequest.put("request", param)
        loginAsync.setOnLoinListener(this, jRequest)
    }

    override fun onLoginSuccess(user: User?) {
        progressDialogUtil.hide()
        if (null != user) {
            UserInfoHolder.getInstance().user = user
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Enter correct username and password", Toast.LENGTH_LONG).show()
        }

    }

}
