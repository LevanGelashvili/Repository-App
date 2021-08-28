package com.flatrocktech.repositoryapp.clean.presentation.starred

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.ReadRepoBriefListUseCase
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.handleLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StarredViewModel @Inject constructor(
    private val readRepoBriefListUseCase: ReadRepoBriefListUseCase
) : ViewModel() {

    private val reposRequestFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val _reposLiveData = MutableLiveData<Result<List<RepoBriefEntity>>>()
    val reposLiveData: LiveData<Result<List<RepoBriefEntity>>> get() = _reposLiveData

    init {
        viewModelScope.launch {
            reposRequestFlow
                .handleLoading(_reposLiveData)
                .map { readRepoBriefListUseCase() }
                .collect { _reposLiveData.postValue(it) }
        }
    }

    fun requestRepositories() {
        reposRequestFlow.tryEmit(Unit)
    }
}