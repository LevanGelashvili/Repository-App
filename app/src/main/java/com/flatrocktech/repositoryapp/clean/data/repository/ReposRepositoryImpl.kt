package com.flatrocktech.repositoryapp.clean.data.repository

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.ReposRemoteDataSource
import com.flatrocktech.repositoryapp.clean.domain.model.entity.RepoEntity
import com.flatrocktech.repositoryapp.clean.domain.model.params.GetReposParams
import com.flatrocktech.repositoryapp.clean.domain.repository.ReposRepository
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.di.CoroutinesModule.IoDispatcher
import com.flatrocktech.repositoryapp.util.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val remoteDataSource: ReposRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ReposRepository {

    override suspend fun getRepos(params: GetReposParams): Result<List<RepoEntity>> {
        return safeApiCall(dispatcher) {
            remoteDataSource.getRepos(params).map {
                RepoEntity(it.repoName)
            }
        }
    }
}