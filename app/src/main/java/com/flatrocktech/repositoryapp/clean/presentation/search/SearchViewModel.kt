package com.flatrocktech.repositoryapp.clean.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoBriefListParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoBriefListUseCase
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.handleLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val fetchRepoBriefListUseCase: FetchRepoBriefListUseCase
) : ViewModel() {

    private val reposRequestFlow = MutableSharedFlow<Pair<String, Boolean>>(extraBufferCapacity = 1)
    private val _reposLiveData = MutableLiveData<Result<List<RepoBriefEntity>>>()
    val reposLiveData: LiveData<Result<List<RepoBriefEntity>>> get() = _reposLiveData

    init {
        viewModelScope.launch {
            reposRequestFlow
                .handleLoading(_reposLiveData)
                .map { (filter, loadMore) ->
                    fetchRepoBriefListUseCase(filter, loadMore)
                }
                .collect { _reposLiveData.postValue(it) }
        }
    }

    fun isLoading(): Boolean {
        return _reposLiveData.value is Result.Loading
    }

    fun requestRepositories(filter: String, loadMore: Boolean) {
        reposRequestFlow.tryEmit(filter to loadMore)
    }
}