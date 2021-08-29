package com.flatrocktech.repositoryapp.clean.domain.usecase.local

import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.util.Result
import javax.inject.Inject

class ReadRepoBriefListUseCase @Inject constructor(
    private val repository: LocalRepoRepository
) {
    suspend operator fun invoke(): Result<List<RepoBriefEntity>> {
        return repository.getRepoBriefList()
    }
}