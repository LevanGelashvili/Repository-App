package com.flatrocktech.repositoryapp.clean.domain.repository

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.GetRepoBriefListParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.GetRepoDetailsParams
import com.flatrocktech.repositoryapp.util.Result

interface RemoteRepoRepository {
    suspend fun getRepoBriefList(params: GetRepoBriefListParams): Result<List<RepoBriefEntity>>
    suspend fun getRepoDetails(params: GetRepoDetailsParams): Result<RepoDetailsEntity>
}