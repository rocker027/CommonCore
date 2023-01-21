package com.coors.commoncore.network.api

import com.coors.commoncore.result.Result
import retrofit2.Response
import kotlin.IllegalStateException

/**
 * @author vinsen
 * @since 2020/11/25
 */
interface ResponseErrorHandler {

    fun <T> processResponse(response: Response<T>): Result<T>

    fun processFailure(throwable: Throwable): Exception
}

class DefaultResponseErrorHandler : ResponseErrorHandler {

    override fun <T> processResponse(response: Response<T>): Result<T> {
        if (response.isSuccessful) {
            return Result.Success(response.body()!!)
        }
        //FIXME customized exception here ~
        return Result.Error(Exception(response.errorBody().toString()))
    }

    override fun processFailure(throwable: Throwable): Exception {
        //FIXME customized exception here ~
        return IllegalStateException(throwable)
    }
}