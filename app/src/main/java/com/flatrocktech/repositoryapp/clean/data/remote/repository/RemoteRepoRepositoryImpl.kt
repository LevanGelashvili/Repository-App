package com.flatrocktech.repositoryapp.clean.data.remote.repository

import com.flatrocktech.repositoryapp.clean.data.remote.api.RepoApi
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.RemoteRepoRepository
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoBriefListParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.mapper.RemoteRepoMapper.toEntity
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.di.CoroutinesModule.IoDispatcher
import com.flatrocktech.repositoryapp.util.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoteRepoRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val api: RepoApi
) : RemoteRepoRepository {

    override suspend fun getRepoBriefList(params: FetchRepoBriefListParams): Result<List<RepoBriefEntity>> {
        return safeApiCall(dispatcher) {
            api.getRepositoryBriefList(
                userFilter = params.userFilter,
                page = params.page,
                perPage = params.perPage
            ).map { it.toEntity() }
        }
    }

    override suspend fun getRepoDetails(params: FetchRepoDetailsParams): Result<RepoDetailsEntity> {
        return safeApiCall(dispatcher) {
            api.getRepositoryDetails(
                owner = params.owner,
                repo = params.repoName
            ).toEntity()
        }
    }
}