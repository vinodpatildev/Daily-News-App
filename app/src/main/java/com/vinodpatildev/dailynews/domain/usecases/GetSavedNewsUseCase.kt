package com.vinodpatildev.dailynews.domain.usecases

import android.util.Log
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>>{
        Log.i("logcat","data observed from : getSavedNewsUseCase")
        return newsRepository.getSavedNews()
    }

}