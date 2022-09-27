package com.vinodpatildev.dailynews.domain.repository

import com.vinodpatildev.dailynews.data.model.ApiResponse
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, page: Int):Resource<ApiResponse>
    suspend fun getSearchedNews(country:String,searchQuery:String,page:Int):Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}