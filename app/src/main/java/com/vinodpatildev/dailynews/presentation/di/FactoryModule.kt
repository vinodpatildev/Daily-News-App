package com.vinodpatildev.dailynews.presentation.di

import android.app.Application
import com.vinodpatildev.dailynews.domain.usecases.*
import com.vinodpatildev.dailynews.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase
    ):NewsViewModelFactory{
        return NewsViewModelFactory(application,getNewsHeadlinesUseCase,getSearchedNewsUseCase,saveNewsUseCase,deleteSavedNewsUseCase,getSavedNewsUseCase)
    }
}