package com.flatrocktech.repositoryapp.clean.domain.repository

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.DeleteRepoBriefParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.IsRepoStarredParams
import com.flatrocktech.repositoryapp.util.Result

interface LocalRepoRepository {
    suspend fun getBriefRepoList(): Result<List<RepoBriefEntity>>
    suspend fun insertBriefRepo(entity: RepoBriefEntity): Result<Unit>
    suspend fun deleteBriefRepo(params: DeleteRepoBriefParams): Result<Unit>
    suspend fun isRepoStarred(params: IsRepoStarredParams): Result<Boolean>
}