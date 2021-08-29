package com.flatrocktech.repositoryapp.util

// Had loading too, but didn't need for this project
sealed class Result<out T> {

    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val errorResponse: ErrorResponse? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorResponse=$errorResponse, exception=$exception]"
        }
    }
}

data class ErrorResponse(
    val code: Int? = null,
    val message: String? = null
)

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data