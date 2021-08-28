package com.flatrocktech.repositoryapp.clean.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val REPOS_TABLE = "repos"

@Entity(
    tableName = REPOS_TABLE
)
data class RepoBriefLocalDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "repo_name") val repoName: String?,
    @ColumnInfo(name = "owner") val owner: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
)