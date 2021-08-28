package com.flatrocktech.repositoryapp.clean.data.datasource.remote.model

import com.squareup.moshi.Json

data class RepoDetailsDto(
    @field:Json(name = "created_at")
    val createdAt: String?,

    @field:Json(name = "description")
    val description: String?,

    @field:Json(name = "language")
    val languageUsed: String?,

    @field:Json(name = "url")
    val url: String?
)