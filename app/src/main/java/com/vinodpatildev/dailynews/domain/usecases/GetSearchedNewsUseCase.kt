package com.vinodpatildev.dailynews.domain.usecases

import com.vinodpatildev.dailynews.data.model.ApiResponse
import com.vinodpatildev.dailynews.data.util.Resource
import com.vinodpatildev.dailynews.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country:String,searchQuery:String,page:Int):Resource<ApiResponse>{
        return newsRepository.getSearchedNews(country,searchQuery,page)
    }
}