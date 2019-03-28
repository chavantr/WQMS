package com.mywings.waterqualitymonitoringsystem

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mywings.waterqualitymonitoringsystem.model.UserInfoHolder
import com.mywings.waterqualitymonitoringsystem.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
                //lblLocation.text = UserInfoHolder.getInstance().location.name
                lid = UserInfoHolder.getInstance().location.id
                lname = UserInfoHolder.getInstance().location.name
            }
        }
    }


    companion object {
        const val SELECT_LOCATION = 759
    }
}
