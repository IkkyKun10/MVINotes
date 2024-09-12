package com.riezki.mvinote.add_note.utils

/**
 * @author riezkymaisyar
 */

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorType: ErrorType? = ErrorType.UNKNOWN_EXCEPTION,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorType: ErrorType?, message: String, data: T? = null) : Resource<T>(data, message, errorType)
    class Loading<T>(data: T? = null) : Resource<T>(data)

    suspend fun onSuccess(block: suspend (T?) -> Unit): Resource<T> {
        if (this is Success) block(data)
        return this
    }

    suspend fun onError(block: suspend (errorType: ErrorType?, message: String?) -> Unit): Resource<T> {
        if (this is Error) block(errorType, message)
        return this
    }

    suspend fun onLoading(block: suspend (data: T?) -> Unit): Resource<T> {
        if (this is Loading) block(data)
        return this
    }
}

enum class ErrorType {
    CLIENT_EXCEPTION,
    SERIALIZATION_EXCEPTION,
    SERVER_EXCEPTION,
    UNKNOWN_EXCEPTION,
    IO_EXCEPTION,
    TIMEOUT_EXCEPTION
}