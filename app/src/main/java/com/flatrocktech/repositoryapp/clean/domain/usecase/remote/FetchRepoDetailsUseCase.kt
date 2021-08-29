package com.flatrocktech.repositoryapp.clean.domain.usecase.remote

import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.RemoteRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class FetchRepoDetailsParams(
    val repoName: String,
    val owner: String
)

class FetchRepoDetailsUseCase @Inject constructor(
    private val repository: RemoteRepoRepository
) {

    suspend operator fun invoke(params: FetchRepoDetailsParams): Result<RepoDetailsEntity> {
        return repository.getRepoDetails(params)
    }
}