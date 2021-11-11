package com.demo.weatherreport.ui.dashboard.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseMVVMFragment
import com.demo.weatherreport.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseMVVMFragment<SettingsViewModel, FragmentSettingsBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override val viewModel: SettingsViewModel by viewModel()

    override fun init() {
        super.init()
        updateUI()
    }

    override fun observers() {

    }

    private fun updateUI() {
        binding.nameTextView.text = getString(R.string.greet_user, viewModel.userName)
        binding.cityTextView.text = viewModel.city
        binding.toggleButton.isChecked = viewModel.isCelsius
        updateToggleText()
        binding.toggleButton.setOnCheckedChangeListener { _, b ->
            viewModel.onToggleChanged(b)
            updateToggleText()
        }
    }

    private fun updateToggleText() {
        binding.toggleButton.setText(if (viewModel.isCelsius) R.string.celsius else R.string.fahrenheit)
    }
}