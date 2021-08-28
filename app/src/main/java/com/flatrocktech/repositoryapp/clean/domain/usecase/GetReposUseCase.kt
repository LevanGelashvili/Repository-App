package com.flatrocktech.repositoryapp.clean.domain.usecase

import com.flatrocktech.repositoryapp.clean.domain.model.entity.RepoEntity
import com.flatrocktech.repositoryapp.clean.domain.model.params.GetReposParams
import com.flatrocktech.repositoryapp.clean.domain.repository.ReposRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

class GetReposUseCase @Inject constructor(
    private val repository: ReposRepository
) {
    suspend operator fun invoke(params: GetReposParams): Result<List<RepoEntity>> {
        return repository.getRepos(params)
    }
}