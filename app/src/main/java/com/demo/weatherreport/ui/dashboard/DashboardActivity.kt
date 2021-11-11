package com.demo.weatherreport.ui.dashboard

import android.content.Context
import android.content.Intent
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseActivity
import com.demo.weatherreport.databinding.ActivityDashboardBinding
import com.demo.weatherreport.utils.AppUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {
    companion object {
        fun launch(context: Context, isCleanRequired: Boolean = true) {
            val intent = Intent(context, DashboardActivity::class.java).apply {
                if (isCleanRequired) flags = AppUtils.addCleanIntent()
            }
            context.startActivity(intent)
        }
    }


    override fun inflateViewBinding() = ActivityDashboardBinding.inflate(layoutInflater)

    override fun init() {
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_dashboard) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_date, R.id.navigation_days
            )
        )
        navView.setupWithNavController(navController)
    }
}