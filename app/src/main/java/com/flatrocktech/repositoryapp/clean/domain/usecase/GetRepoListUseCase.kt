package com.flatrocktech.repositoryapp.clean.domain.usecase

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.ReposRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class GetRepoListParams(
    val userFilter: String,
    val page: Int,
    val perPage: Int
)

class GetRepoListUseCase @Inject constructor(
    private val repository: ReposRepository
) {
    suspend operator fun invoke(params: GetRepoListParams): Result<List<RepoBriefEntity>> {
        return repository.getRepoBriefList(params)
    }
}