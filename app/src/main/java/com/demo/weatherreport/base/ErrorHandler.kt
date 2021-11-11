package com.demo.weatherreport.base

import android.util.Log
import com.demo.weatherreport.data.model.error.ErrorData
import com.demo.weatherreport.data.model.error.ErrorResponse
import com.demo.weatherreport.data.model.error.ErrorType
import com.demo.weatherreport.network.NoConnectivityException
import com.demo.weatherreport.utils.AppConst.ERROR_401
import com.demo.weatherreport.utils.AppConst.ERROR_403
import com.demo.weatherreport.utils.AppConst.ERROR_404
import com.demo.weatherreport.utils.AppConst.ERROR_422
import com.squareup.moshi.Moshi
import org.koin.java.KoinJavaComponent.getKoin
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorHandler {
    fun parseError(throwable: Throwable): ErrorData {
        Log.e("Error", throwable.stackTraceToString())
        return when (throwable) {
            is HttpException -> {
                val response = throwable.response()

                try {
                    val errorResponseAdapter =
                        (getKoin().get() as Moshi).adapter(ErrorResponse::class.java)
                    val errorResponse =
                        errorResponseAdapter.fromJson(response?.errorBody()?.string().orEmpty())
                            ?: return ErrorData()

                    ErrorData(
                        errorType = when (errorResponse.code) {
                            ERROR_401 -> ErrorType.UNAUTHORIZED_ACCESS
                            ERROR_422 -> ErrorType.VALIDATION_ERROR
                            ERROR_403 -> ErrorType.FORBIDDEN_ERROR
                            ERROR_404 -> ErrorType.NOT_FOUND
                            else -> ErrorType.OTHER
                        },
                        errorResponse = errorResponse
                    )
                } catch (e: Exception) {
                    ErrorData()
                }
            }
            is SocketTimeoutException -> ErrorData(errorType = ErrorType.SERVER_TIMEOUT)
            is IOException -> {
                if (throwable is NoConnectivityException) ErrorData(errorType = ErrorType.NO_INTERNET)
                else ErrorData()
            }
            else -> ErrorData()
        }
    }
}