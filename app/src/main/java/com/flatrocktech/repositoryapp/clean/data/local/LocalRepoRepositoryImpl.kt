package com.flatrocktech.repositoryapp.clean.data.local

import com.flatrocktech.repositoryapp.clean.data.local.room.RepoDao
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.DeleteRepoBriefParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.IsRepoStarredParams
import com.flatrocktech.repositoryapp.clean.mapper.LocalRepoMapper.toDto
import com.flatrocktech.repositoryapp.clean.mapper.LocalRepoMapper.toEntity
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.di.CoroutinesModule.IoDispatcher
import com.flatrocktech.repositoryapp.util.storage.safeStorageCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LocalRepoRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val dao: RepoDao,
) : LocalRepoRepository {

    override suspend fun getBriefRepoList(): Result<List<RepoBriefEntity>> {
        return safeStorageCall(dispatcher) {
            dao.getBriefRepoList().map { it.toEntity() }
        }
    }

    override suspend fun isRepoStarred(params: IsRepoStarredParams): Result<Boolean> {
        return safeStorageCall(dispatcher) {
            dao.checkIfRepoExists(repoName = params.repoName)
        }
    }

    override suspend fun insertBriefRepo(entity: RepoBriefEntity): Result<Unit> {
        return safeStorageCall(dispatcher) {
            dao.insertBriefRepo(repo = entity.toDto())
        }
    }

    override suspend fun deleteBriefRepo(params: DeleteRepoBriefParams): Result<Unit> {
        return safeStorageCall(dispatcher) {
            dao.deleteRepo(repoName = params.repoName)
        }
    }
}