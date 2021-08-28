package com.flatrocktech.repositoryapp.clean.data.repository

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.ReposRemoteDataSource
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.ReposRepository
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoListParams
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.di.CoroutinesModule.IoDispatcher
import com.flatrocktech.repositoryapp.util.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val remoteDataSource: ReposRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ReposRepository {

    override suspend fun getRepoBriefList(params: GetRepoListParams): Result<List<RepoBriefEntity>> {
        return safeApiCall(dispatcher) {
            remoteDataSource.getRepoBriefList(params).map { repoDto ->
                RepoBriefEntity(
                    repoName = repoDto.repoName,
                    owner = repoDto.owner.ownerName,
                    avatarUrl = repoDto.owner.avatarUrl
                )
            }
        }
    }

    override suspend fun getRepoDetails(params: GetRepoDetailsParams): Result<RepoDetailsEntity> {
        return safeApiCall(dispatcher) {
            val repoDetailsDto = remoteDataSource.getRepoDetails(params)
            RepoDetailsEntity(
                createdAt = repoDetailsDto.createdAt,
                description = repoDetailsDto.description,
                languageUsed = repoDetailsDto.languageUsed,
                url = repoDetailsDto.url
            )
        }
    }
}