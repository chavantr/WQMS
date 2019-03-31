package com.mywings.waterqualitymonitoringsystem


import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mywings.waterqualitymonitoringsystem.model.Sensor
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.GetSensorAsync
import com.mywings.waterqualitymonitoringsystem.process.OnGetSensorListener
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.nav_header_dashboard.view.*
import java.text.DecimalFormat


class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnGetSensorListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil
    private var lid: Int = 0
    private var lname: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        progressDialogUtil = ProgressDialogUtil(this)
        lid = UserInfoHolder.getInstance().user.lid
        lname = UserInfoHolder.getInstance().user.lname
        lblLocation.text = lname
        lblLocation.setOnClickListener {
            val intent = Intent(this@DashboardActivity, SelectLocationActivity::class.java)
            startActivityForResult(intent, SELECT_LOCATION)
        }
        val view = nav_view.getHeaderView(0)
        var lblName = view.lblName
        lblName.text = UserInfoHolder.getInstance().user.name
        var lblEmail = view.lblEmail
        lblEmail.text = UserInfoHolder.getInstance().user.email
        init()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                val intent = Intent(this@DashboardActivity, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_LOCATION) {
                lid = UserInfoHolder.getInstance().location.id
                lname = UserInfoHolder.getInstance().location.name
                lblLocation.text = lname
                init()
            }
        }
    }

    private fun init() {
        progressDialogUtil.show()
        val getSensorAsync = GetSensorAsync()
        getSensorAsync.setOnSensorListener(this, "?id=$lid")
    }

    override fun onSensorSuccess(result: List<Sensor>?) {
        progressDialogUtil.hide()
        if (result!!.isNotEmpty()) {
            val x = result[0].ph
            val decimalFormat = DecimalFormat("#.##")
            val newV = decimalFormat.format(x.toDouble())
            lblPh.text = "Ph level is : $newV"
            lblTemperature.text = "Temperature is : ${decimalFormat.format(result[0].temperature.toDouble())}"
            if (newV.toDouble() in 6.5..8.5) {
                lblInstruction.text = "Water quality is : Good"
            } else {
                lblInstruction.text = "Water quality is : Poor"
                showNotification()
            }
        } else {
            lblPh.text = "Ph level is : NA"
            lblTemperature.text = "Temperature is : NA"
        }
    }

    private fun showNotification() {
        val intent = Intent(this, DashboardActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val b = NotificationCompat.Builder(this)

        b.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.notification_icon_background)
            .setTicker("Water quality")
            .setContentTitle("Your water quality is poor")
            .setContentText("Your water quality is very poor, please check the water tank water supply pipes.")
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setContentIntent(contentIntent)
            .setContentInfo("Info")
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, b.build())
    }

    companion object {
        const val SELECT_LOCATION = 759
    }
}
