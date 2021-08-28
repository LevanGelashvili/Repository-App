package com.flatrocktech.repositoryapp.clean.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.*
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoDetailsUseCase
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.handleLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val fetchRepoDetailsUseCase: FetchRepoDetailsUseCase,
    private val isRepoStarredUseCase: IsRepoStarredUseCase,
    private val cacheRepoBriefUseCase: CacheRepoBriefUseCase,
    private val deleteRepoBriefUseCase: DeleteRepoBriefUseCase
) : ViewModel() {

    private val repoDetailsRequestFlow = MutableSharedFlow<FetchRepoDetailsParams>(extraBufferCapacity = 1)
    private val _repoDetailsLiveData = MutableLiveData<Result<RepoDetailsEntity>>()
    val repoDetailsLiveData: LiveData<Result<RepoDetailsEntity>> get() = _repoDetailsLiveData

    private val isRepoStarredRequestFlow = MutableSharedFlow<IsRepoStarredParams>(extraBufferCapacity = 1)
    private val _isRepoStarredLiveData = MutableLiveData<Result<Boolean>>()
    val isRepoStarredLiveData: LiveData<Result<Boolean>> get() = _isRepoStarredLiveData

    private val starRepoRequestFlow = MutableSharedFlow<RepoBriefEntity>(extraBufferCapacity = 1)
    private val _starRepoStatusLiveData = MutableLiveData<Result<Unit>>()
    val starRepoStatusLiveData: LiveData<Result<Unit>> get() = _starRepoStatusLiveData

    private val unstarRepoRequestFlow = MutableSharedFlow<DeleteRepoBriefParams>(extraBufferCapacity = 1)
    private val _unstarRepoStatusLiveData = MutableLiveData<Result<Unit>>()
    val unstarRepoStatusLiveData: LiveData<Result<Unit>> get() = _unstarRepoStatusLiveData

    init {
        viewModelScope.launch {
            repoDetailsRequestFlow
                .handleLoading(_repoDetailsLiveData)
                .map { fetchRepoDetailsUseCase(it) }
                .collect { _repoDetailsLiveData.postValue(it) }
        }
        viewModelScope.launch {
            isRepoStarredRequestFlow
                .handleLoading(_isRepoStarredLiveData)
                .map { isRepoStarredUseCase(it) }
                .collect { _isRepoStarredLiveData.postValue(it) }
        }
        viewModelScope.launch {
            starRepoRequestFlow
                .handleLoading(_starRepoStatusLiveData)
                .map { cacheRepoBriefUseCase(it) }
                .collect { _starRepoStatusLiveData.postValue(it) }
        }
        viewModelScope.launch {
            unstarRepoRequestFlow
                .handleLoading(_unstarRepoStatusLiveData)
                .map { deleteRepoBriefUseCase(it) }
                .collect { _unstarRepoStatusLiveData.postValue(it) }
        }
    }

    fun fetchRepoDetails(repoName: String, owner: String) {
        repoDetailsRequestFlow.tryEmit(
            FetchRepoDetailsParams(
                repoName = repoName,
                owner = owner
            )
        )
    }

    fun checkIsRepoStarred(repoName: String) {
        isRepoStarredRequestFlow.tryEmit(
            IsRepoStarredParams(repoName = repoName)
        )
    }

    fun starRepo(repoBrief: RepoBriefEntity) {
        starRepoRequestFlow.tryEmit(repoBrief)
    }

    fun unstarRepo(repoName: String) {
        unstarRepoRequestFlow.tryEmit(
            DeleteRepoBriefParams(repoName = repoName)
        )
    }
}