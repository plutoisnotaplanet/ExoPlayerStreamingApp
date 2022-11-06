package com.plutoisnotaplanet.exoplayerstreamingapp.domain.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class Response<out T> {

    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val error: Throwable) : Response<Nothing>()
    object Loading : Response<Nothing>()

    val isError: Boolean
        get() = this is Error

    val isSuccess: Boolean
        get() = this !is Error

    fun getValueOrNull(): T? {
        return if (this is Success) return this.data
        else null
    }

    fun exceptionOrNull(): Throwable? =
        if (this is Error) this.error
        else null

}

inline fun  <T: Any> runResulting(block: () -> T): Response<T> {
    return try {
        Response.Success(block())
    } catch (e: Throwable) {
        Response.Error(e)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T: Any> Response<T>.onSuccess(action: (value: T) -> Unit): Response<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (isSuccess) action(this.getValueOrNull() as T)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T: Any> Response<T>.onFailure(action: (exception: Throwable) -> Unit): Response<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    exceptionOrNull()?.let { action(it) }
    return this
}

fun error(error: Throwable): Response.Error = Response.Error(error)

fun <T: Any> success(result: T): Response<T> = Response.Success(result)