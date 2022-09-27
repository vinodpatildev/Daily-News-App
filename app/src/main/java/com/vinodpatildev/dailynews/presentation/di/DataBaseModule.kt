package com.vinodpatildev.dailynews.presentation.di

import android.app.Application
import androidx.room.Room
import com.vinodpatildev.dailynews.data.db.ArticleDao
import com.vinodpatildev.dailynews.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(app: Application):ArticleDatabase{
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            "article_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(articleDb:ArticleDatabase): ArticleDao {
        return articleDb.getArticleDao()
    }
}