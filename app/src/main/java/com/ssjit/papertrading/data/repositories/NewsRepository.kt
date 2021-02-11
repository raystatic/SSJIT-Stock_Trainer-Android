package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.remote.ApiService
import com.ssjit.papertrading.data.remote.RapidApiService
import com.ssjit.papertrading.data.remote.RapisApiClient
import javax.inject.Inject

class NewsRepository @Inject constructor(
        private val apiService: ApiService
){

    private var rapidApiService:RapidApiService =RapisApiClient.getApiClient()!!.create(RapidApiService::class.java)
    init {

    }

    suspend fun getNews() = rapidApiService.getNews(
            apiKey = "38250c4b43msh116db1bd6158524p1c38fdjsn55921a99baae",
            apiHost = "apidojo-yahoo-finance-v1.p.rapidapi.com",
            useQueryString = true,
            category = "generalnews",
            region = "IN"
    )

}