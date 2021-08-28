package com.flatrocktech.repositoryapp.clean.domain.usecase

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.RemoteRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class FetchRepoBriefListParams(
    val userFilter: String,
    val page: Int,
    val perPage: Int
)

class FetchRepoBriefListUseCase @Inject constructor(
    private val repository: RemoteRepoRepository
) {
    suspend operator fun invoke(params: FetchRepoBriefListParams): Result<List<RepoBriefEntity>> {
        return repository.getRepoBriefList(params)
    }
}