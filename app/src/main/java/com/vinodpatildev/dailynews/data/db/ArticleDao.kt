package com.vinodpatildev.dailynews.data.db

import androidx.room.*
import com.vinodpatildev.dailynews.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete()
    suspend fun delete(article:Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles() : Flow<List<Article>>

    @Query("DELETE FROM articles")
    fun deleteAllArticles()
}