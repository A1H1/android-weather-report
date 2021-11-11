package com.demo.weatherreport.base

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseMVVMFragment<T : ViewModel, VB : ViewBinding> : BaseFragment<VB>() {
    protected abstract val viewModel: T

    override fun init() {
        observers()
    }

    abstract fun observers()
    open fun showLoading(isLoading: Boolean) {}
}