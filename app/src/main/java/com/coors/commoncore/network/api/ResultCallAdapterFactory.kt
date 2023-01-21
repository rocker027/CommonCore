package com.coors.commoncore.network.api

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import com.coors.commoncore.result.Result

/**
 * @author vinsen
 * @since 2020/11/12
 */
abstract class CallDelegate<T, R>(
    protected val proxy: Call<T>
) : Call<R> {
    override fun execute(): Response<R> = executeImpl()
    final override fun enqueue(callback: Callback<R>) = enqueueImpl(callback)
    final override fun clone(): Call<R> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled
    override fun timeout(): Timeout = proxy.timeout()

    abstract fun executeImpl(): Response<R>
    abstract fun enqueueImpl(callback: Callback<R>)
    abstract fun cloneImpl(): Call<R>
}

class ResultCall<T : Any>(
    proxy: Call<T>,
    private val errorHandler: ResponseErrorHandler
) : CallDelegate<T, Result<T>>(proxy) {

    override fun executeImpl(): Response<Result<T>> {
        val result = try {
            val response = proxy.execute()
            errorHandler.processResponse(response)
        } catch (t: Throwable) {
            Result.Error(errorHandler.processFailure(t))
        }
        return Response.success(result)
    }

    override fun enqueueImpl(callback: Callback<Result<T>>) = proxy.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result = try {
                errorHandler.processResponse(response)
            } catch (t: Throwable) {
                Result.Error(errorHandler.processFailure(t))
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = Result.Error(errorHandler.processFailure(t))
            Log.e("ResultCall", "onFailure: $result")
            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone(), errorHandler)
}

class ResultAdapter(
    private val responseType: Type,
    private val errorHandler: ResponseErrorHandler
) : CallAdapter<Type, Call<Result<Type>>> {
    override fun responseType() = responseType
    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call, errorHandler)
}

class ResultCallAdapterFactory(
    private val errorHandler: ResponseErrorHandler
) : CallAdapter.Factory() {

    companion object {
        fun create(
            errorHandler: ResponseErrorHandler = DefaultResponseErrorHandler()
        ): ResultCallAdapterFactory = ResultCallAdapterFactory(errorHandler)
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType =
                        getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType, errorHandler)
                }
                else -> null
            }
        }
        else -> null
    }
}