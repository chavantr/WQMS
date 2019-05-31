package com.mywings.waterqualitymonitoringsystem.process

import android.os.AsyncTask
import com.mywings.waterqualitymonitoringsystem.model.User
import org.json.JSONObject

class LoginAsync : AsyncTask<JSONObject, Void, User?>() {

    private lateinit var onLoginListener: OnLoginListener

    override fun doInBackground(vararg param: JSONObject?): User? {
        var response: String? = HttpConnectionUtil().requestPost(WQMConstants.URL + WQMConstants.LOGIN, param[0])
        return if (response.isNullOrEmpty()) return null else {
            var user = User()
            val jResponse = JSONObject(response)
            user.id = jResponse.getInt("Id")
            user.name = jResponse.getString("Name")
            user.email = jResponse.getString("Email")
            user.username = jResponse.getString("Username")
            user.password = jResponse.getString("Password")
            user.lid = jResponse.getInt("LId")
            user.number = jResponse.getString("Number")
            user.lname = jResponse.getString("LName")
            user
        }
    }

    override fun onPostExecute(result: User?) {
        super.onPostExecute(result)
        onLoginListener.onLoginSuccess(result)
    }

    fun setOnLoinListener(onLoginListener: OnLoginListener, request: JSONObject) {
        this.onLoginListener = onLoginListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}