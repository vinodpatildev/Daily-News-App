package com.vinodpatildev.dailynews.domain.usecases

import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)

}