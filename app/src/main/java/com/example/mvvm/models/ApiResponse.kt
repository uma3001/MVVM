package com.example.mvvm.models

import com.example.mvvm.models.Article

data class ApiResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)