package com.mywings.waterqualitymonitoringsystem.process

import android.os.AsyncTask
import com.mywings.waterqualitymonitoringsystem.model.States
import org.json.JSONObject

class GetLatestState : AsyncTask<String, Void, States?>() {

    private val httpConnectionUtil = HttpConnectionUtil()
    private lateinit var onGetStateListener: OnGetStateListener

    override fun doInBackground(vararg param: String?): States? {
        var response: String? = null
        response = httpConnectionUtil.requestGet(WQMConstants.URL + WQMConstants.WQM_GET_NEXT_STATE)

        if (response.isNullOrEmpty()) return null else {
            val jNode = JSONObject(response)
            var states = States();
            states.state = jNode.getString("WaterState")
            states.next = jNode.getString("NextDate")
            return states
        }

    }

    override fun onPostExecute(result: States?) {
        super.onPostExecute(result)
        onGetStateListener.onStateSuccess(result)
    }

    fun setOnStateListener(onGetStateListener: OnGetStateListener, request: String?) {
        this.onGetStateListener = onGetStateListener;
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }


}