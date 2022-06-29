package com.example.mvvm.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.models.ApiResponse
import com.example.mvvm.repositry.NewsRepositry
import com.example.mvvm.utills.Resources
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsrepositry: NewsRepositry
):AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resources<ApiResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: ApiResponse? = null

    init {
        getBreakingNews("india")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resources.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsrepositry.getBreakingNews(countryCode,breakingNewsPage)
                breakingNews.postValue(handlingNewsResponse(response))
            } else {
                breakingNews.postValue(Resources.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resources.Error("Network Failure"))
                else -> breakingNews.postValue(Resources.Error("Conversion Error"))
            }
        }
    }

    private fun handlingNewsResponse(response: Response<ApiResponse>): Resources<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    //oldArticles?.addAll(newArticles)
                }
                return Resources.Success(breakingNewsResponse?: resultResponse)
            }
        }
        return Resources.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}

