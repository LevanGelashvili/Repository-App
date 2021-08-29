package com.flatrocktech.repositoryapp.clean.data.remote.api.model

import com.squareup.moshi.Json

data class RepoBriefRemoteDto(
    @field:Json(name = "name")
    val repoName: String?,

    val owner: OwnerRemoteDto?
)

data class OwnerRemoteDto(
    @field:Json(name = "login")
    val ownerName: String?,

    @field:Json(name = "avatar_url")
    val avatarUrl: String?
)
