package com.flatrocktech.repositoryapp.clean.data.datasource.remote.model

import com.squareup.moshi.Json

data class RepoDto(

    @field:Json(name = "name")
    val repoName: String,

//    @field:Json(name = "userId")
//    val userName: String,
//
//    @field:Json(name = "userId")
//    val avatarUrl: String,
)
