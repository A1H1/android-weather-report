package com.demo.weatherreport.data.model.error

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ErrorResponse(
    @field:Json(name = "code")
    val code: String = "0",
    @field:Json(name = "message")
    val message: String? = null,
    @field:Json(name = "error")
    val error: String? = null
)

enum class ErrorType {
    OTHER,
    UNAUTHORIZED_ACCESS,
    VALIDATION_ERROR,
    FORBIDDEN_ERROR,
    NOT_FOUND,
    SERVER_TIMEOUT,
    NO_INTERNET
}

data class ErrorData(
    val errorResponse: ErrorResponse = ErrorResponse(),
    val errorType: ErrorType = ErrorType.OTHER
)