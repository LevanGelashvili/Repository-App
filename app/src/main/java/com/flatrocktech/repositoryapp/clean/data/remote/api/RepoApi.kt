package com.flatrocktech.repositoryapp.clean.data.remote.api

import com.flatrocktech.repositoryapp.clean.data.remote.api.model.RepoBriefRemoteDto
import com.flatrocktech.repositoryapp.clean.data.remote.api.model.RepoDetailsRemoteDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApi {

    @GET("users/{user}/repos")
    suspend fun getRepositoryBriefList(
        @Path("user") userFilter: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepoBriefRemoteDto>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepoDetailsRemoteDto
}