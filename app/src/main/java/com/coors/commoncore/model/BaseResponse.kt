package com.coors.commoncore.model

data class BaseResponse<T>(
    val code: Int,
    val data: T,
    val message: String
)