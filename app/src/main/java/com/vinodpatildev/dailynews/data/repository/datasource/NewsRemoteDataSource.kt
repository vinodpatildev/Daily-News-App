package com.vinodpatildev.dailynews.data.repository.datasource

import com.vinodpatildev.dailynews.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country:String, page:Int): Response<ApiResponse>
    suspend fun getSearchedHeadlines(country:String, searchQuery:String, page:Int): Response<ApiResponse>
}