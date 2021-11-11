package com.demo.weatherreport.base

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.demo.weatherreport.R
import com.demo.weatherreport.data.model.error.ErrorDTO
import com.demo.weatherreport.data.model.error.ErrorResponse
import com.demo.weatherreport.ui.error.ErrorBottomSheet

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), BaseErrorInterface,
    ErrorBottomSheet.ErrorInterface {
    companion object {
        const val MIN_KEYBOARD_HEIGHT_PX = 150
    }

    protected abstract fun inflateViewBinding(): T
    protected open fun init() {}
    protected open fun clickListeners() {}
    protected open fun isKeyboardListenerRequired() = false
    private var errorBottomSheet: ErrorBottomSheet? = null
    private lateinit var _binding: T
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateViewBinding()
        setContentView(_binding.root)
        init()
        clickListeners()

        if (isKeyboardListenerRequired())
            initKeyBoardListener()
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
        errorBottomSheet!!.show(supportFragmentManager)
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
        errorBottomSheet!!.show(supportFragmentManager)
    }

    override fun onUnauthorizedAccess(errorResponse: ErrorResponse) {
        super.onUnauthorizedAccess(errorResponse)
        finish()
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
        errorBottomSheet!!.show(supportFragmentManager)
    }

    private fun initKeyBoardListener() {
        val decorView: View = window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            private val windowVisibleDisplayFrame: Rect = Rect()
            private var lastVisibleDecorViewHeight = 0
            override fun onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
                val visibleDecorViewHeight: Int = windowVisibleDisplayFrame.height()
                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        onKeyBoardVisible()
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        onKeyBoardHide()
                    }
                }
                lastVisibleDecorViewHeight = visibleDecorViewHeight
            }
        })
    }

    open fun onKeyBoardVisible() {}
    open fun onKeyBoardHide() {}
}