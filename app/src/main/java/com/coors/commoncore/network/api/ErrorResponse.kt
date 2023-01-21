package com.coors.commoncore.network.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author vinsen
 * @since 2021/5/7
 */
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "status")
    val status: Boolean,
    @Json(name = "error")
    val error: String,
    @Json(name = "error_msg")
    val errorMessage: String,
)
