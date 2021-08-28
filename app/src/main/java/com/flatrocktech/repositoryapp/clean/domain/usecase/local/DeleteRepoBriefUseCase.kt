package com.flatrocktech.repositoryapp.clean.domain.usecase.local

import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

data class DeleteRepoBriefParams(
    val repoName: String
)

class DeleteRepoBriefUseCase @Inject constructor(
    private val repository: LocalRepoRepository
) {

    suspend operator fun invoke(params: DeleteRepoBriefParams): Result<Unit> {
        return repository.deleteBriefRepo(params)
    }
}