package com.flatrocktech.repositoryapp.clean.domain.usecase.local

import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class IsRepoStarredParams(
    val repoName: String,
    val ownerName: String
)

class IsRepoStarredUseCase @Inject constructor(
    private val repository: LocalRepoRepository
) {
    suspend operator fun invoke(params: IsRepoStarredParams): Result<Boolean> {
        return repository.isRepoStarred(params)
    }
}