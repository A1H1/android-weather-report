package com.demo.weatherreport.ui.dashboard.days

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseMVVMFragment
import com.demo.weatherreport.data.model.event.DataType
import com.demo.weatherreport.databinding.FragmentDaysBinding
import com.demo.weatherreport.di.utils.EventObserver
import com.demo.weatherreport.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class DaysFragment : BaseMVVMFragment<DaysViewModel, FragmentDaysBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDaysBinding.inflate(inflater, container, false)

    override val viewModel: DaysViewModel by viewModel()

    private lateinit var adapter: DaysAdapter

    override fun init() {
        super.init()
        viewModel.getForecast()
        updateUI()
    }

    override fun observers() {
        viewModel.weatherInfo.observe(this, EventObserver {
            showLoading(it.isLoading)
            when (it.type) {
                DataType.SUCCESS -> adapter.setItems(it.data!!)
                DataType.ERROR -> showError(it.error!!)
            }
        })
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressBarGroup.show(isLoading)
    }

    private fun updateUI() {
        adapter = DaysAdapter(lifecycleScope)
        binding.reportRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reportRecyclerView.adapter = adapter
        binding.nameTextView.text = getString(R.string.greet_user, viewModel.userName)
        binding.cityTextView.text = viewModel.city
    }
}