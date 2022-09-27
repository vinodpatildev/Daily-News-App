package com.vinodpatildev.dailynews.data.repository.datasource

import com.vinodpatildev.dailynews.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)

    suspend fun deleteArticleFromDB(article:Article)

    fun getAllArticlesFromDB(): Flow<List<Article>>
}