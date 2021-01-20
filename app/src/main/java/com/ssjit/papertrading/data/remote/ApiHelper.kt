package com.ssjit.papertrading.data.remote

import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.LoginResponse
import com.ssjit.papertrading.data.models.search.SearchResponse
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import retrofit2.Response
import retrofit2.http.POST

interface ApiHelper {

    suspend fun login(loginRequest: LoginRequest):Response<LoginResponse>

    suspend fun searchStock(keyword:String):Response<SearchResponse>

    suspend fun getStockInfo(symbol:String):Response<StockDetailResponse>

}