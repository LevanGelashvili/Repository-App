package com.flatrocktech.repositoryapp.clean.domain.repository

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.DeleteRepoBriefParams
import com.flatrocktech.repositoryapp.clean.domain.usecase.local.IsRepoBriefStarredParams
import com.flatrocktech.repositoryapp.util.Result

interface LocalRepoRepository {
    suspend fun getRepoBriefList(): Result<List<RepoBriefEntity>>
    suspend fun insertRepoBrief(entity: RepoBriefEntity): Result<Unit>
    suspend fun deleteRepoBrief(params: DeleteRepoBriefParams): Result<Unit>
    suspend fun isRepoBriefStarred(params: IsRepoBriefStarredParams): Result<Boolean>
}