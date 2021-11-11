package com.demo.weatherreport.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.demo.weatherreport.R
import com.demo.weatherreport.data.model.error.ErrorDTO
import com.demo.weatherreport.data.model.error.ErrorResponse
import com.demo.weatherreport.ui.error.ErrorBottomSheet

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseErrorInterface,
    ErrorBottomSheet.ErrorInterface {
    protected abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    protected open fun setOnClickListeners() {}
    protected open fun init() {}
    private var errorBottomSheet: ErrorBottomSheet? = null
    private lateinit var _binding: T
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(inflater, container)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setOnClickListeners()
    }

    override fun showInternetError() {
        errorBottomSheet?.dismiss()
        errorBottomSheet = ErrorBottomSheet.createInstance(
            ErrorDTO(
                title = getString(R.string.oops_no_internet_connectivity),
                subTitle = getString(R.string.no_internet_message),
                icon = R.drawable.ic_no_wifi
            )
        )
        errorBottomSheet!!.setErrorInterface(this)
        errorBottomSheet!!.show(childFragmentManager)
    }

    override fun showSocketTimeoutError() {
        errorBottomSheet?.dismiss()
        errorBottomSheet = ErrorBottomSheet.createInstance(
            ErrorDTO(
                title = getString(R.string.server_timeout),
                subTitle = getString(R.string.please_try_again_later)
            )
        )
        errorBottomSheet!!.setErrorInterface(this)
        errorBottomSheet!!.show(childFragmentManager)
    }

    override fun showOtherError(errorResponse: ErrorResponse) {
        errorBottomSheet?.dismiss()
        errorBottomSheet = ErrorBottomSheet.createInstance(
            ErrorDTO(
                title = "Oops!",
                subTitle = errorResponse.error ?: errorResponse.message
                ?: getString(R.string.something_went_wrong)
            )
        )
        errorBottomSheet!!.setErrorInterface(this)
        errorBottomSheet!!.show(childFragmentManager)
    }
}