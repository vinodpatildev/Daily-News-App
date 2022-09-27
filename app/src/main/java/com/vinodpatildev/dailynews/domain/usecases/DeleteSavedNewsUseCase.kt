package com.vinodpatildev.dailynews.domain.usecases

import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}