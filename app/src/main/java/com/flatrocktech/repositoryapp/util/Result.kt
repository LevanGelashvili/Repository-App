package com.flatrocktech.repositoryapp.util

import com.flatrocktech.repositoryapp.util.ui.viewmodel.RequestCode

sealed class Result<out T> {

    data class Success<out T>(
        val data: T,
        var requestCode: RequestCode? = null
    ) : Result<T>()

    data class Error(val errorResponse: ErrorResponse? = null) : Result<Nothing>()

    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorResponse=$errorResponse]"
            Loading -> "Loading"
        }
    }
}

data class ErrorResponse(
    val code: Int? = null,
    val message: String? = null
)

fun <T> Result<T>.setRequestCode(newRequestCode: RequestCode?): Result<T> {
    return when (this) {
        is Result.Success -> this.apply {
            requestCode = newRequestCode
        }
        else -> this
    }
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data