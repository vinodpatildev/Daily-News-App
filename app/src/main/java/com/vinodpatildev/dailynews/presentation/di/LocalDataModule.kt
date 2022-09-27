package com.vinodpatildev.dailynews.presentation.di

import com.vinodpatildev.dailynews.data.db.ArticleDao
import com.vinodpatildev.dailynews.data.repository.datasource.NewsLocalDataSource
import com.vinodpatildev.dailynews.data.repository.datasourceimpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideNewsLocalDataSource(articleDao: ArticleDao):NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDao)
    }
}