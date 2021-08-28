package com.flatrocktech.repositoryapp.clean.domain.repository

import com.flatrocktech.repositoryapp.clean.domain.model.entity.RepoEntity
import com.flatrocktech.repositoryapp.clean.domain.model.params.GetReposParams
import com.flatrocktech.repositoryapp.util.Result

interface ReposRepository {
    suspend fun getRepos(params: GetReposParams): Result<List<RepoEntity>>
}