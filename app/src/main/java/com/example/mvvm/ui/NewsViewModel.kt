package com.example.mvvm.ui

import androidx.lifecycle.ViewModel
import com.example.mvvm.repositry.NewsRepositry

class NewsViewModel(
    val  newsrepositry: NewsRepositry
):ViewModel() {
}