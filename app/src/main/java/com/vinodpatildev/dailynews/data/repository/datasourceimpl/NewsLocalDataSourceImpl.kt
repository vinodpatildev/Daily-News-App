package com.vinodpatildev.dailynews.data.repository.datasourceimpl

import android.util.Log
import androidx.lifecycle.liveData
import com.vinodpatildev.dailynews.data.db.ArticleDao
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDao: ArticleDao):NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        return articleDao.insert(article)
    }

    override suspend fun deleteArticleFromDB(article: Article) {
        articleDao.delete(article)
    }

    override fun getAllArticlesFromDB(): Flow<List<Article>> {
        Log.i("logcat","data observed from : NewsLocalDataSourceImpl start")
        return articleDao.getAllArticles()
    }

}