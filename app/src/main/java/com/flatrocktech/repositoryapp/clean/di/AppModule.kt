package com.flatrocktech.repositoryapp.clean.di

import com.flatrocktech.repositoryapp.clean.data.datasource.remote.ReposRemoteDataSource
import com.flatrocktech.repositoryapp.clean.data.datasource.remote.ReposRemoteDataSourceImpl
import com.flatrocktech.repositoryapp.clean.data.datasource.remote.api.ReposApi
import com.flatrocktech.repositoryapp.clean.data.repository.ReposRepositoryImpl
import com.flatrocktech.repositoryapp.clean.domain.repository.ReposRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideReposApi(retrofit: Retrofit): ReposApi {
        return retrofit.create(ReposApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ReposDataModule {

    @Binds
    abstract fun bindsReposRepository(
        repository: ReposRepositoryImpl
    ): ReposRepository

    @Binds
    abstract fun bindsReposRemoteDataSource(
        dataSource: ReposRemoteDataSourceImpl
    ): ReposRemoteDataSource
}