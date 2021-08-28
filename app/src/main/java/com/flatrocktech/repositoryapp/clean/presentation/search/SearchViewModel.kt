package com.flatrocktech.repositoryapp.clean.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatrocktech.repositoryapp.clean.domain.model.entity.RepoEntity
import com.flatrocktech.repositoryapp.clean.domain.model.params.GetReposParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetReposUseCase
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.handleLoading
import com.flatrocktech.repositoryapp.util.setRequestCode
import com.flatrocktech.repositoryapp.util.ui.viewmodel.RequestCodes.RC_INIT
import com.flatrocktech.repositoryapp.util.ui.viewmodel.RequestCodes.RC_LOAD_MORE
import com.flatrocktech.repositoryapp.util.ui.viewmodel.RequestCodes.RC_SEARCH
import com.flatrocktech.repositoryapp.util.ui.viewmodel.RequestCodes.RC_SWIPE_REFRESH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase
) : ViewModel() {

    private val reposRequestFlow = MutableSharedFlow<GetReposParams>(extraBufferCapacity = 1)
    private val _reposLiveData = MutableLiveData<Result<List<RepoEntity>>>()
    val reposLiveData: LiveData<Result<List<RepoEntity>>> get() = _reposLiveData

    private var currentPage = STARTING_PAGE

    var lastRequestCode = RC_INIT
    private set

    init {
        viewModelScope.launch {
            reposRequestFlow
                .handleLoading(_reposLiveData)
                .map {
                    getReposUseCase(it)
                }
                .collect {
                    _reposLiveData.postValue(it.setRequestCode(lastRequestCode))
                }
        }
    }

    fun onRefresh(filter: String) {
        lastRequestCode = RC_SWIPE_REFRESH
        currentPage = STARTING_PAGE
        requestRepositories(filter, currentPage)
    }

    fun onSearch(filter: String) {
        lastRequestCode = RC_SEARCH
        currentPage = STARTING_PAGE
        requestRepositories(filter, currentPage)
    }

    fun onLoadMore(filter: String) {
        lastRequestCode = RC_LOAD_MORE
        requestRepositories(filter, currentPage)
        currentPage += 1
    }

    private fun requestRepositories(
        filter: String,
        page: Int,
    ) {
        reposRequestFlow.tryEmit(
            GetReposParams(
                userFilter = filter,
                page = page,
                perPage = TAKE_N
            )
        )
    }

    companion object {
        private const val STARTING_PAGE = 0
        const val TAKE_N = 15
    }
}