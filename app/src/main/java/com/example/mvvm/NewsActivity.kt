package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.db.ArticleDatabase
import com.example.mvvm.repositry.NewsRepositry
import com.example.mvvm.ui.NewsViewModel
import com.example.mvvm.ui.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepositry = NewsRepositry(ArticleDatabase(this))
        val viewModelProviderFactory:NewsViewModelProviderFactory(newsRepositry)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
    }
}