package com.demo.weatherreport.data.model.error

import com.demo.weatherreport.R
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ErrorDTO(
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "subTitle")
    val subTitle: String = "",
    @field:Json(name = "primaryButtonText")
    val primaryButtonText: String = "",
    @field:Json(name = "secondaryButtonText")
    val secondaryButtonText: String = "",
    @field:Json(name = "icon")
    val icon: Int = R.drawable.ic_error
)