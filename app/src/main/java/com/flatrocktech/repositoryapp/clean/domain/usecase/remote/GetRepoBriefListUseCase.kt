package com.flatrocktech.repositoryapp.clean.domain.usecase.remote

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.RemoteRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class GetRepoBriefListParams(
    val userFilter: String,
    val page: Int,
    val perPage: Int
)

class GetRepoBriefListUseCase @Inject constructor(
    private val repository: RemoteRepoRepository
) {

    private var currentPage = STARTING_PAGE_INDEX

    suspend operator fun invoke(filter: String, loadMore: Boolean): Result<List<RepoBriefEntity>> {
        val repoList = repository.getRepoBriefList(
            GetRepoBriefListParams(
                userFilter = filter,
                page = if (loadMore) currentPage else STARTING_PAGE_INDEX,
                perPage = TAKE_N
            )
        )
        if (!loadMore) {
            currentPage = STARTING_PAGE_INDEX
        }
        currentPage += 1
        return repoList
    }

    companion object {
        const val TAKE_N = 13
        private const val STARTING_PAGE_INDEX = 1
    }
}