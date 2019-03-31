package com.mywings.waterqualitymonitoringsystem.process

import android.os.AsyncTask
import com.mywings.waterqualitymonitoringsystem.model.Sensor
import org.json.JSONArray

class GetSensorAsync : AsyncTask<String?, Void, List<Sensor>?>() {

    private lateinit var onGetSensorListener: OnGetSensorListener

    override fun doInBackground(vararg param: String?): List<Sensor>? {
        val response = HttpConnectionUtil().requestGet(WQMConstants.URL + WQMConstants.GET_SENSOR_INFO + param[0])
        return if (response.isNullOrEmpty()) null else {
            val jSensor = JSONArray(response)
            var lst = ArrayList<Sensor>()
            for (i in 0..(jSensor.length() - 1)) {
                val jNode = jSensor.getJSONObject(i)
                var node = Sensor()
                node.id = jNode.getInt("Id")
                node.name = jNode.getString("Name")
                node.ph = jNode.getString("Ph")
                node.temperature = jNode.getString("Temperature")
                lst.add(node)
            }
            lst
        }
    }

    override fun onPostExecute(result: List<Sensor>?) {
        super.onPostExecute(result)
        onGetSensorListener.onSensorSuccess(result)
    }

    fun setOnSensorListener(onGetSensorListener: OnGetSensorListener, request: String?) {
        this.onGetSensorListener = onGetSensorListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}