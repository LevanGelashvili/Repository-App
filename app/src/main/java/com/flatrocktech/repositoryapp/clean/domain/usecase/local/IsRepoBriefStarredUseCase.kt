package com.flatrocktech.repositoryapp.clean.domain.usecase.local

import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class IsRepoBriefStarredParams(
    val repoName: String,
    val owner: String
)

class IsRepoBriefStarredUseCase @Inject constructor(
    private val repository: LocalRepoRepository
) {
    suspend operator fun invoke(params: IsRepoBriefStarredParams): Result<Boolean> {
        return repository.isRepoBriefStarred(params)
    }
}