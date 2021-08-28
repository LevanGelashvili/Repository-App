package com.flatrocktech.repositoryapp.clean.di

import android.content.Context
import androidx.room.Room
import com.flatrocktech.repositoryapp.clean.data.local.LocalRepoRepositoryImpl
import com.flatrocktech.repositoryapp.clean.data.local.room.RepoDao
import com.flatrocktech.repositoryapp.clean.data.local.room.RepoDatabase
import com.flatrocktech.repositoryapp.clean.data.remote.api.RepoApi
import com.flatrocktech.repositoryapp.clean.data.remote.repository.RemoteRepoRepositoryImpl
import com.flatrocktech.repositoryapp.clean.domain.repository.LocalRepoRepository
import com.flatrocktech.repositoryapp.clean.domain.repository.RemoteRepoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideReposApi(retrofit: Retrofit): RepoApi {
        return retrofit.create(RepoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepoDatabase(@ApplicationContext app: Context): RepoDatabase {
        return Room.databaseBuilder(
            app,
            RepoDatabase::class.java,
            "repos"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(database: RepoDatabase): RepoDao {
        return database.repoDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepoDataModule {

    @Binds
    abstract fun bindsRemoteRepoRepository(
        repository: RemoteRepoRepositoryImpl
    ): RemoteRepoRepository

    @Binds
    abstract fun bindsLocalRepoRepository(
        repository: LocalRepoRepositoryImpl
    ): LocalRepoRepository
}