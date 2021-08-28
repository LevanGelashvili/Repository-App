package com.flatrocktech.repositoryapp.clean.data.datasource.remote.api

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.model.RepoDetailsDto
import com.flatrocktech.repositoryapp.clean.data.datasource.remote.model.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReposApi {

    @GET("users/{user}/repos")
    suspend fun getRepositoryBriefList(
        @Path("user") userFilter: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepoDto>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepoDetailsDto
}