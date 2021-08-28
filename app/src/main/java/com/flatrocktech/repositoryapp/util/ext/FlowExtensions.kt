package com.flatrocktech.repositoryapp.util.ext

import androidx.lifecycle.MutableLiveData
import com.flatrocktech.repositoryapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

fun <T, R> Flow<T>.handleLoading(liveData: MutableLiveData<Result<R>>) = onEach {
    liveData.postValue(Result.Loading)
}