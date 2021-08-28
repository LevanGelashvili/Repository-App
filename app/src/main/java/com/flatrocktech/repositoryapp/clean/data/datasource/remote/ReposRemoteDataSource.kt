package com.flatrocktech.repositoryapp.clean.data.datasource.remote

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.api.ReposApi
import com.flatrocktech.repositoryapp.clean.data.datasource.remote.model.RepoDto
import com.flatrocktech.repositoryapp.clean.domain.model.params.GetReposParams
import javax.inject.Inject

interface ReposRemoteDataSource {
    suspend fun getRepos(params: GetReposParams): List<RepoDto>
}

class ReposRemoteDataSourceImpl @Inject constructor(
    private val api: ReposApi
) : ReposRemoteDataSource {

    override suspend fun getRepos(params: GetReposParams): List<RepoDto> {
        return api.getRepositories(params.userFilter)
    }
}