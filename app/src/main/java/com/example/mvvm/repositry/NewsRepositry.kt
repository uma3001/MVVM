package com.example.mvvm.repositry

import com.example.mvvm.api.Retrofit
import com.example.mvvm.db.ArticleDatabase

class NewsRepositry(val db:ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        Retrofit.api.getnews(countryCode,pageNumber)
}