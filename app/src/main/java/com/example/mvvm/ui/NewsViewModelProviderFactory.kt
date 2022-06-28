package com.example.mvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.repositry.NewsRepositry

class NewsViewModelProviderFactory(
    newsRepositry: NewsRepositry
):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsrepositry) as T
    }
}