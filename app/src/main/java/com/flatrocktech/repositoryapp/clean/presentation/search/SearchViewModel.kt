package com.flatrocktech.repositoryapp.clean.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.GetRepoBriefListUseCase
import com.flatrocktech.repositoryapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val getRepoBriefListUseCase: GetRepoBriefListUseCase
) : ViewModel() {

    private val briefReposRequestFlow = MutableSharedFlow<Pair<String, Boolean>>(extraBufferCapacity = 1)
    private val _briefRepos = MutableLiveData<Result<List<RepoBriefEntity>>>()
    val briefRepos: LiveData<Result<List<RepoBriefEntity>>> get() = _briefRepos

    init {
        viewModelScope.launch {
            briefReposRequestFlow
                .map { (filter, loadMore) ->
                    getRepoBriefListUseCase(filter, loadMore)
                }
                .collect { _briefRepos.postValue(it) }
        }
    }

    fun requestRepositories(filter: String, loadMore: Boolean) {
        briefReposRequestFlow.tryEmit(filter to loadMore)
    }
}