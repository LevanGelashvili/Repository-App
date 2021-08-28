package com.flatrocktech.repositoryapp.clean.data.datasource.remote.model

import com.squareup.moshi.Json

data class RepoDto(
    @field:Json(name = "name")
    val repoName: String,

    val owner: OwnerDto
)

data class OwnerDto(
    @field:Json(name = "login")
    val ownerName: String,

    @field:Json(name = "avatar_url")
    val avatarUrl: String?
)
