package com.vinodpatildev.dailynews.data.repository.datasourceimpl

import com.vinodpatildev.dailynews.data.api.NewsApiService
import com.vinodpatildev.dailynews.data.model.ApiResponse
import com.vinodpatildev.dailynews.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsApiService: NewsApiService): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<ApiResponse> {
        return newsApiService.getTopNewsHeadlines(country,page)
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<ApiResponse> {
        return newsApiService.getSearchedNewsHeadlines(country,searchQuery,page)
    }
}