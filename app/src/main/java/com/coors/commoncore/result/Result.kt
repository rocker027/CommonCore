package com.coors.commoncore.result

/**
 * @author gregho
 * @since 2020/10/6
 *
 * A generic class that holds a value with its status.
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "[SUCCESS] data: $data"
            is Error -> "[ERROR] exception: $exception"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(block: T): T {
    return (this as? Result.Success<T>)?.data ?: block
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val Result<*>.exception: Exception?
    get() = (this as? Result.Error)?.exception

inline fun <reified T, reified U> Result<T>.map(mapper: (result: T) -> U): Result<U> {
    return when (this) {
        is Result.Success -> Result.Success(mapper(data))
        is Result.Error -> this
    }
}

inline fun <reified T> Result<T>.mapError(mapper: (exception: Exception) -> Exception): Result<T> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> Result.Error(mapper(exception))
    }
}

inline fun <reified T, reified U> Result<T>.flatMap(mapper: (T) -> Result<U>): Result<U> {
    return when (this) {
        is Result.Success -> mapper(data)
        is Result.Error -> this
    }
}

inline fun <reified T> Result<T>.flatMapError(mapper: (exception: Exception) -> Result.Error): Result<T> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> mapper(exception)
    }
}