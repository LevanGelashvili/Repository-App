package com.flatrocktech.repositoryapp.clean.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatrocktech.repositoryapp.clean.data.local.room.model.RepoBriefLocalDto

@Database(entities = [RepoBriefLocalDto::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}