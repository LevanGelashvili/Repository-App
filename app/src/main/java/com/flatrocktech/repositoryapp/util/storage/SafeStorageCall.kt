package com.flatrocktech.repositoryapp.util.storage

import com.flatrocktech.repositoryapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeStorageCall(
    dispatcher: CoroutineDispatcher,
    storageCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(storageCall.invoke())
        } catch (throwable: Throwable) {
            Result.Error(exception = throwable)
        }
    }
}