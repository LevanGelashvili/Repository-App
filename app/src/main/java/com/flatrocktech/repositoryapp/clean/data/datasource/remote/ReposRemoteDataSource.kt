package com.flatrocktech.repositoryapp.clean.data.datasource.remote

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.api.ReposApi
import com.flatrocktech.repositoryapp.clean.data.datasource.remote.model.RepoDetailsDto
import com.flatrocktech.repositoryapp.clean.data.datasource.remote.model.RepoDto
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoDetailsParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoListParams
import javax.inject.Inject

interface ReposRemoteDataSource {
    suspend fun getRepoBriefList(params: GetRepoListParams): List<RepoDto>
    suspend fun getRepoDetails(params: GetRepoDetailsParams): RepoDetailsDto
}

class ReposRemoteDataSourceImpl @Inject constructor(
    private val api: ReposApi
) : ReposRemoteDataSource {

    override suspend fun getRepoBriefList(params: GetRepoListParams): List<RepoDto> {
        return api.getRepositoryBriefList(
            userFilter = params.userFilter,
            page = params.page,
            perPage = params.perPage
        )
    }

    override suspend fun getRepoDetails(params: GetRepoDetailsParams): RepoDetailsDto {
        return api.getRepositoryDetails(
            owner = params.owner,
            repo = params.repoName
        )
    }
}