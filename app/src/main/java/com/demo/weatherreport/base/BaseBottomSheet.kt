package com.demo.weatherreport.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.demo.weatherreport.R
import com.demo.weatherreport.data.model.error.ErrorResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<T : ViewBinding> : BottomSheetDialogFragment(),
    BaseErrorInterface {
    protected abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    protected abstract fun tag(): String
    protected open fun setOnClickListeners() {}
    protected open fun init() {}
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.color.black70Opacity)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setOnClickListeners()
    }

    override fun showInternetError() {
        Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_LONG).show()
    }

    override fun showSocketTimeoutError() {
        Toast.makeText(requireContext(), R.string.server_timeout, Toast.LENGTH_LONG).show()
    }

    override fun showOtherError(errorResponse: ErrorResponse) {
        Toast.makeText(
            requireContext(),
            errorResponse.message ?: getString(R.string.something_went_wrong),
            Toast.LENGTH_LONG
        ).show()
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, tag())
    }
}