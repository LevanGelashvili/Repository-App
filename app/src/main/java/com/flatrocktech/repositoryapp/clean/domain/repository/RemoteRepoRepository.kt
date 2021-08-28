package com.flatrocktech.repositoryapp.clean.domain.repository

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.FetchRepoBriefListParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoDetailsParams
import com.flatrocktech.repositoryapp.util.Result

interface RemoteRepoRepository {
    suspend fun getRepoBriefList(params: FetchRepoBriefListParams): Result<List<RepoBriefEntity>>
    suspend fun getRepoDetails(params: FetchRepoDetailsParams): Result<RepoDetailsEntity>
}