package com.flatrocktech.repositoryapp.clean.data.remote.model

import com.squareup.moshi.Json

data class RepoDetailsRemoteDto(
    @field:Json(name = "created_at")
    val createdAt: String?,

    @field:Json(name = "description")
    val description: String?,

    @field:Json(name = "language")
    val languageUsed: String?,

    @field:Json(name = "svn_url")
    val url: String?
)