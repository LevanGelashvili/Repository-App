package com.flatrocktech.repositoryapp.clean.domain.usecase.local

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

class SaveRepoBriefUseCase @Inject constructor(
    private val repository: LocalRepoRepository
) {
    suspend operator fun invoke(entity: RepoBriefEntity): Result<Unit> {
        return repository.insertRepoBrief(entity)
    }
}