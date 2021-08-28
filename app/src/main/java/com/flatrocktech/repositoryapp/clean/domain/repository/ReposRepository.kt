package com.flatrocktech.repositoryapp.clean.domain.repository

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoListParams
import com.flatrocktech.repositoryapp.util.Result

interface ReposRepository {
    suspend fun getRepoBriefList(params: GetRepoListParams): Result<List<RepoBriefEntity>>
    suspend fun getRepoDetails(params: GetRepoDetailsParams): Result<RepoDetailsEntity>
}