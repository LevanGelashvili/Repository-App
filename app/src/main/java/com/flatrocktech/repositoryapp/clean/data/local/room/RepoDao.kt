package com.flatrocktech.repositoryapp.clean.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatrocktech.repositoryapp.clean.data.local.room.model.REPOS_TABLE
import com.flatrocktech.repositoryapp.clean.data.local.room.model.RepoBriefLocalDto

@Dao
interface RepoDao {

    @Query("SELECT * FROM $REPOS_TABLE")
    fun getBriefRepoList(): List<RepoBriefLocalDto>

    @Query("SELECT EXISTS (SELECT * FROM $REPOS_TABLE WHERE repo_name = :repoName)")
    fun checkIfRepoExists(repoName: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBriefRepo(repo: RepoBriefLocalDto)

    @Query("DELETE FROM $REPOS_TABLE WHERE repo_name = :repoName AND owner = :owner")
    fun deleteRepo(repoName: String, owner: String)
}