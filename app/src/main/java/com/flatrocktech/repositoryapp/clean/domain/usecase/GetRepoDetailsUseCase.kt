package com.flatrocktech.repositoryapp.clean.domain.usecase

import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.ReposRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class GetRepoDetailsParams(
    val owner: String,
    val repoName: String
)

class GetRepoDetailsUseCase @Inject constructor(
    private val repository: ReposRepository
) {

    suspend operator fun invoke(params: GetRepoDetailsParams): Result<RepoDetailsEntity> {
        return repository.getRepoDetails(params)
    }
}