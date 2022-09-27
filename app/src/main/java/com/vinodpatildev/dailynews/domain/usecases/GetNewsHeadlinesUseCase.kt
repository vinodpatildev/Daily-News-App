package com.vinodpatildev.dailynews.domain.usecases

import com.vinodpatildev.dailynews.data.model.ApiResponse
import com.vinodpatildev.dailynews.data.util.Resource
import com.vinodpatildev.dailynews.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int):Resource<ApiResponse>{
        return newsRepository.getNewsHeadlines(country, page)
    }

}