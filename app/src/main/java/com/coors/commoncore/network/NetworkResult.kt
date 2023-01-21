package com.coors.commoncore.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

//sealed class NetworkResult<T : Any> {
//    class Success<T: Any>(val data: T) : NetworkResult<T>()
//    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
//    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
//}
//
//
//sealed interface ApiResult<T : Any>
//
//class ApiSuccess<T : Any>(val data: T) : ApiResult<T>
//class ApiError<T : Any>(val code: Int, val message: String?) : ApiResult<T>
//class ApiException<T : Any>(val e: Throwable) : ApiResult<T>
//
//suspend fun <T : Any> handleApi(
//    execute: suspend () -> Response<T>
//): ApiResult<T> {
//    return try {
//        val response = execute()
//        val body = response.body()
//        if (response.isSuccessful && body != null) {
//            ApiSuccess(body)
//        } else {
//            ApiError(code = response.code(), message = response.message())
//        }
//    } catch (e: HttpException) {
//        ApiError(code = e.code(), message = e.message())
//    } catch (e: Throwable) {
//        ApiException(e)
//    }
//}
//
//class NetworkResultCall<T : Any>(
//    private val proxy: Call<T>
//) : Call<NetworkResult<T>> {
//
//    override fun enqueue(callback: Callback<NetworkResult<T>>) {
//        proxy.enqueue(object : Callback<T> {
//            override fun onResponse(call: Call<T>, response: Response<T>) {
//                val networkResult = handleApi { response }
//                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
//            }
//
//            override fun onFailure(call: Call<T>, t: Throwable) {
//                val networkResult = ApiException<T>(t)
//                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
//            }
//        })
//    }
//
//    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
//    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())
//    override fun request(): Request = proxy.request()
//    override fun timeout(): Timeout = proxy.timeout()
//    override fun isExecuted(): Boolean = proxy.isExecuted
//    override fun isCanceled(): Boolean = proxy.isCanceled
//    override fun cancel() { proxy.cancel() }
//}

