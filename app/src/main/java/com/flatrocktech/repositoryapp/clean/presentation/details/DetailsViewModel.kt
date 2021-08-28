package com.flatrocktech.repositoryapp.clean.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoDetailsUseCase
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoListParams
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.handleLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase
) : ViewModel() {

    private val repoDetailsRequestFlow = MutableSharedFlow<GetRepoDetailsParams>(extraBufferCapacity = 1)
    private val _repoDetailsLiveData = MutableLiveData<Result<RepoDetailsEntity>>()
    val repoDetailsLiveData: LiveData<Result<RepoDetailsEntity>> get() = _repoDetailsLiveData

    init {
        viewModelScope.launch {
            repoDetailsRequestFlow
                .handleLoading(_repoDetailsLiveData)
                .map { getRepoDetailsUseCase(it) }
                .collect { _repoDetailsLiveData.postValue(it) }
        }
    }

    fun requestRepoDetails(params: GetRepoDetailsParams) {
        repoDetailsRequestFlow.tryEmit(params)
    }
}