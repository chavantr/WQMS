package com.mywings.waterqualitymonitoringsystem.process

import android.os.AsyncTask
import org.json.JSONObject

class UpdateProfileAsync : AsyncTask<JSONObject, Void, String?>() {

    private lateinit var onUpdateProfileListener: OnUpdateProfileListener

    override fun doInBackground(vararg param: JSONObject?): String? {
        return HttpConnectionUtil().requestPost(WQMConstants.URL + WQMConstants.WQM_UPDATE_PROFILE, param[0])
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        onUpdateProfileListener.onProfileUpdateSuccess(result)
    }

    fun setOnUpdateProfileListener(onUpdateProfileListener: OnUpdateProfileListener, request: JSONObject) {
        this.onUpdateProfileListener = onUpdateProfileListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}