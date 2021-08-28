package com.flatrocktech.repositoryapp.clean.domain.model.params

data class GetReposParams(
    val userFilter: String,
    val page: Int,
    val perPage: Int
)
