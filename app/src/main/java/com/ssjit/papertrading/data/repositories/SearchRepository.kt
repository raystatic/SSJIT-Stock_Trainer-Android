package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.models.search.SearchResponse
import com.ssjit.papertrading.data.remote.ApiHelper
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {

    suspend fun searchStock(keyword:String): Response<SearchResponse> {
        return apiHelper.searchStock(keyword)
    }

}