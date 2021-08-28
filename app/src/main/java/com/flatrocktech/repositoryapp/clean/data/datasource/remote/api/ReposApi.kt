package com.flatrocktech.repositoryapp.clean.data.datasource.remote.api

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.model.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ReposApi {

    @GET("users/{user}/repos")
    suspend fun getRepositories(@Path("user") userFilter: String): List<RepoDto>
}