package com.mywings.waterqualitymonitoringsystem.process

import android.os.AsyncTask
import org.json.JSONObject

class RegistrationAsync : AsyncTask<JSONObject, Void, String?>() {

    private lateinit var onRegistrationListener: OnRegistrationListener

    override fun doInBackground(vararg param: JSONObject?): String? {
        return HttpConnectionUtil().requestPost(WQMConstants.URL + WQMConstants.REGISTRATION, param[0])
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        onRegistrationListener.onRegistration(result)
    }

    fun setOnRegistrationListener(onRegistrationListener: OnRegistrationListener, request: JSONObject) {
        this.onRegistrationListener = onRegistrationListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}