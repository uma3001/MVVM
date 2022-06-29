package com.example.mvvm.api

import com.example.mvvm.models.ApiResponse
import com.example.mvvm.utills.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getnews(
        @Query("country")
        Countrycode:String = "india",
        @Query("page")
        Pagenumber: Int = 1,
        @Query("apikey")
        apikey:String = API_KEY
    ): Response<ApiResponse>
}