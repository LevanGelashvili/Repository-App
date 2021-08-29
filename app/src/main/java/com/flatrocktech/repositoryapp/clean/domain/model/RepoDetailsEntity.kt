package com.flatrocktech.repositoryapp.clean.domain.model

data class RepoDetailsEntity(
    val createdAt: String?,
    val description: String?,
    val languageUsed: String?,
    val url: String?,
    val subscriberCount: Int?
)
