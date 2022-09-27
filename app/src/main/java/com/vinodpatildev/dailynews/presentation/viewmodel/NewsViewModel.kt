package com.vinodpatildev.dailynews.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vinodpatildev.dailynews.data.model.ApiResponse
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.data.util.Resource
import com.vinodpatildev.dailynews.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase
):AndroidViewModel(app) {
    val newsHeadlines: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val searchedNewsHeadlines : MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getNewsHeadlines(country:String, page:Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                val apiResult = getNewsHeadlinesUseCase.execute(country,page)
                newsHeadlines.postValue(apiResult)
            }else{
                newsHeadlines.postValue(Resource.Error("Internet is not available."))
            }
        }catch (e:Exception){
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getSearchedNewsHeadlines(country:String,searchQuery:String,page:Int) = viewModelScope.launch(Dispatchers.IO){
        searchedNewsHeadlines.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                val apiResult = getSearchedNewsUseCase.execute(country,searchQuery, page)
                searchedNewsHeadlines.postValue(apiResult)
            }else{
                searchedNewsHeadlines.postValue(Resource.Error("Internet is not available."))
            }
        }catch (e:Exception){
            searchedNewsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun saveNewsArticle(article:Article) = viewModelScope.launch(Dispatchers.IO){
        Log.i("logcat","article saved")
        saveNewsUseCase.execute(article)
    }

    fun deleteNewsArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedNewsUseCase.execute(article)
    }

    fun getSavedNewsArticles() = liveData{
        Log.i("logcat","data observed from :getSavedNewsArticles() = liveData")
        getSavedNewsUseCase.execute().collect{
            Log.i("logcat","data observed from saved news viewmodel")
            emit(it)
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}