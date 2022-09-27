package com.vinodpatildev.dailynews.presentation.di

import com.vinodpatildev.dailynews.data.api.NewsApiService
import com.vinodpatildev.dailynews.data.repository.datasource.NewsRemoteDataSource
import com.vinodpatildev.dailynews.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(newsApiService: NewsApiService):NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}