package com.flatrocktech.repositoryapp.clean.mapper

import com.flatrocktech.repositoryapp.clean.data.local.room.model.RepoBriefLocalDto
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity

object LocalRepoMapper {

    fun RepoBriefLocalDto.toEntity(): RepoBriefEntity {
        return RepoBriefEntity(repoName, owner, avatarUrl)
    }

    fun RepoBriefEntity.toDto(): RepoBriefLocalDto {
        return RepoBriefLocalDto(0, repoName, owner, avatarUrl)
    }
}