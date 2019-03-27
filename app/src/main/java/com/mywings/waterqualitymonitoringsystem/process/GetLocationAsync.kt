package com.mywings.waterqualitymonitoringsystem.process

import android.os.AsyncTask
import com.mywings.waterqualitymonitoringsystem.model.Location
import org.json.JSONArray

class GetLocationAsync : AsyncTask<Void, Void, List<Location>?>() {

    private lateinit var onGetLocationListener: OnGetLocationListener

    override fun doInBackground(vararg param: Void?): List<Location>? {
        val response = HttpConnectionUtil().requestGet(WQMConstants.URL + WQMConstants.GET_LOCATION)
        val jResponse = JSONArray(response)
        return run {
            var lst = ArrayList<Location>()
            for (i in 0..(jResponse.length() - 1)) {
                var location = Location()
                val jNode = jResponse.getJSONObject(i)
                location.id = jNode.getInt("Id")
                location.name = jNode.getString("Name")
                lst.add(location)
            }
            lst
        }
    }

    override fun onPostExecute(result: List<Location>?) {
        super.onPostExecute(result)
        onGetLocationListener.onLocationSuccess(result)
    }

    fun setOnLocationListener(onGetLocationListener: OnGetLocationListener) {
        this.onGetLocationListener = onGetLocationListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}