package com.flatrocktech.repositoryapp.clean.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.*
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.GetRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.GetRepoDetailsUseCase
import com.flatrocktech.repositoryapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase,
    private val isRepoBriefStarredUseCase: IsRepoBriefStarredUseCase,
    private val saveRepoBriefUseCase: SaveRepoBriefUseCase,
    private val deleteRepoBriefUseCase: DeleteRepoBriefUseCase
) : ViewModel() {

    private val repoDetailsRequestFlow = MutableSharedFlow<GetRepoDetailsParams>(extraBufferCapacity = 1)
    private val _repoDetailsLiveData = MutableLiveData<Result<RepoDetailsEntity>>()
    val repoDetailsLiveData: LiveData<Result<RepoDetailsEntity>> get() = _repoDetailsLiveData

    private val isRepoStarredRequestFlow = MutableSharedFlow<IsRepoBriefStarredParams>(extraBufferCapacity = 1)
    private val _isRepoStarred = MutableLiveData<Result<Boolean>>()
    val isRepoStarred: LiveData<Result<Boolean>> get() = _isRepoStarred

    private val starRepoRequestFlow = MutableSharedFlow<RepoBriefEntity>(extraBufferCapacity = 1)
    private val _starRepo = MutableLiveData<Result<Unit>>()
    val starRepo: LiveData<Result<Unit>> get() = _starRepo

    private val unstarRepoRequestFlow = MutableSharedFlow<DeleteRepoBriefParams>(extraBufferCapacity = 1)
    private val _unstarRepo = MutableLiveData<Result<Unit>>()
    val unstarRepo: LiveData<Result<Unit>> get() = _unstarRepo

    init {
        viewModelScope.launch {
            repoDetailsRequestFlow
                .map { getRepoDetailsUseCase(it) }
                .collect { _repoDetailsLiveData.postValue(it) }
        }
        viewModelScope.launch {
            isRepoStarredRequestFlow
                .map { isRepoBriefStarredUseCase(it) }
                .collect { _isRepoStarred.postValue(it) }
        }
        viewModelScope.launch {
            starRepoRequestFlow
                .map { saveRepoBriefUseCase(it) }
                .collect { _starRepo.postValue(it) }
        }
        viewModelScope.launch {
            unstarRepoRequestFlow
                .map { deleteRepoBriefUseCase(it) }
                .collect { _unstarRepo.postValue(it) }
        }
    }

    fun fetchRepoDetails(repoName: String, owner: String) {
        repoDetailsRequestFlow.tryEmit(
            GetRepoDetailsParams(repoName = repoName, owner = owner)
        )
    }

    fun checkIsRepoStarred(repoName: String, owner: String) {
        isRepoStarredRequestFlow.tryEmit(
            IsRepoBriefStarredParams(repoName = repoName, owner = owner)
        )
    }

    fun starRepo(repoBrief: RepoBriefEntity) {
        starRepoRequestFlow.tryEmit(repoBrief)
    }

    fun unstarRepo(repoName: String, owner: String) {
        unstarRepoRequestFlow.tryEmit(
            DeleteRepoBriefParams(repoName = repoName, owner = owner)
        )
    }
}