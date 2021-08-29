package com.flatrocktech.repositoryapp.clean.mapper

import com.flatrocktech.repositoryapp.clean.data.remote.api.model.RepoBriefRemoteDto
import com.flatrocktech.repositoryapp.clean.data.remote.api.model.RepoDetailsRemoteDto
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity

object RemoteRepoMapper {

    // Since DTOs are filtered before being converted to entity, !! can be used safely
    fun RepoBriefRemoteDto.toEntity(): RepoBriefEntity {
        return RepoBriefEntity(
            repoName!!, owner!!.ownerName!!, owner.avatarUrl
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