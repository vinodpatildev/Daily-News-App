package com.vinodpatildev.dailynews.presentation.di

import com.vinodpatildev.dailynews.data.repository.NewsRepositoryImpl
import com.vinodpatildev.dailynews.data.repository.datasource.NewsLocalDataSource
import com.vinodpatildev.dailynews.data.repository.datasource.NewsRemoteDataSource
import com.vinodpatildev.dailynews.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource, newsLocalDataSource: NewsLocalDataSource):NewsRepository{
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}