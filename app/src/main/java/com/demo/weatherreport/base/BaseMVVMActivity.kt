package com.demo.weatherreport.base

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseMVVMActivity<T : ViewModel, VB : ViewBinding> : BaseActivity<VB>() {
    protected abstract val viewModel: T

    override fun init() {
        observers()
    }

    abstract fun observers()
    open fun showLoading(isLoading: Boolean) {}
}