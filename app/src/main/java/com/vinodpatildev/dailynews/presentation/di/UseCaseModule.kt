package com.vinodpatildev.dailynews.presentation.di

import com.vinodpatildev.dailynews.domain.repository.NewsRepository
import com.vinodpatildev.dailynews.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideNewsHeadlinesUseCase(newsRepository: NewsRepository):GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedNewsHeadlinesUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase{
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository):SaveNewsUseCase{
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(newsRepository: NewsRepository):DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSavedNewsUseCase(newsRepository: NewsRepository):GetSavedNewsUseCase{
        return GetSavedNewsUseCase(newsRepository)
    }

}