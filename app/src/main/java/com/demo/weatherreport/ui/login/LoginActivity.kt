package com.demo.weatherreport.ui.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseMVVMActivity
import com.demo.weatherreport.databinding.ActivityLoginBinding
import com.demo.weatherreport.di.utils.EventObserver
import com.demo.weatherreport.ui.dashboard.DashboardActivity
import com.demo.weatherreport.utils.AppUtils
import com.demo.weatherreport.utils.setDebounceClickListener
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LoginActivity : BaseMVVMActivity<LoginViewModel, ActivityLoginBinding>() {
    companion object {
        fun launch(context: Context, isCleanRequired: Boolean = true) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                if (isCleanRequired) flags = AppUtils.addCleanIntent()
            }
            context.startActivity(intent)
        }
    }

    override fun inflateViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override val viewModel: LoginViewModel by viewModel()

    override fun init() {
        super.init()
        checkForLocationPermission()
    }

    override fun clickListeners() {
        binding.loginButton.setDebounceClickListener {
            hideError()
            val name = binding.nameEditText.text.toString()
            if (name.length < 3) displayNameError(R.string.invalid_name)
            else viewModel.login(name)
        }
    }

    override fun observers() {
        viewModel.loginStatus.observe(this, EventObserver {
            if (it) DashboardActivity.launch(this)
            else checkForLastLocation()
        })
    }

    private fun hideError() {
        binding.nameErrorMessageTextView.setTextColor(
            ContextCompat.getColor(this, R.color.tertiaryText)
        )
        binding.nameErrorMessageTextView.setText(R.string.name)
    }

    private fun displayNameError(error: Int) {
        binding.nameErrorMessageTextView.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding.nameErrorMessageTextView.setText(error)
    }

    private fun checkForLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        false
                    ) -> checkForLastLocation()
                    else -> displayNameError(R.string.location_required)
                }
            }

            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }
        checkForLastLocation()
    }

    @SuppressLint("MissingPermission")
    private fun checkForLastLocation() {
        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    getCurrentCity(location)
                } else {
                    displayNameError(R.string.something_went_wrong)
                }
            }
    }

    private fun getCurrentCity(location: Location) {
        val addresses: List<Address> = Geocoder(this, Locale.getDefault()).getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        if (addresses.isNotEmpty()) {
            viewModel.onLocationFound(addresses.first())
        } else {
            displayNameError(R.string.something_went_wrong)
        }
    }
}