package com.vinodpatildev.dailynews.data.repository

import android.util.Log
import com.vinodpatildev.dailynews.data.model.ApiResponse
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.data.repository.datasource.NewsLocalDataSource
import com.vinodpatildev.dailynews.data.repository.datasource.NewsRemoteDataSource
import com.vinodpatildev.dailynews.data.util.Resource
import com.vinodpatildev.dailynews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedHeadlines(country,searchQuery,page))
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticleFromDB(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        Log.i("logcat","data observed from : NewsRepositoryImpl")
        return newsLocalDataSource.getAllArticlesFromDB()
    }
    fun responseToResource(response: Response<ApiResponse>):Resource<ApiResponse>{
        if(response.isSuccessful){
            response.body()?.let{result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}