package com.demo.weatherreport.ui.splash

import android.annotation.SuppressLint
import com.demo.weatherreport.base.BaseMVVMActivity
import com.demo.weatherreport.databinding.ActivitySplashBinding
import com.demo.weatherreport.di.utils.EventObserver
import com.demo.weatherreport.ui.dashboard.DashboardActivity
import com.demo.weatherreport.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseMVVMActivity<SplashViewModel, ActivitySplashBinding>() {
    override fun inflateViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override val viewModel: SplashViewModel by viewModel()

    override fun init() {
        super.init()
        viewModel.checkUserStatus()
    }

    override fun observers() {
        viewModel.userStatus.observe(this, EventObserver {
            if (it) LoginActivity.launch(this)
            else DashboardActivity.launch(this)
        })
    }
}