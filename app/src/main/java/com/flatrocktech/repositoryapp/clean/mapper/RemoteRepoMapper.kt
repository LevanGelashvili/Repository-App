package com.flatrocktech.repositoryapp.clean.mapper

import com.flatrocktech.repositoryapp.clean.data.remote.model.RepoBriefRemoteDto
import com.flatrocktech.repositoryapp.clean.data.remote.model.RepoDetailsRemoteDto
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity

object RemoteRepoMapper {

    fun RepoBriefRemoteDto.toEntity(): RepoBriefEntity {
        return RepoBriefEntity(
            repoName, owner.ownerName, owner.avatarUrl
        )
    }

    fun RepoDetailsRemoteDto.toEntity(): RepoDetailsEntity {
        return RepoDetailsEntity(
            createdAt,
            description,
            languageUsed,
            url,
            subscriberCount
        )
    }
}