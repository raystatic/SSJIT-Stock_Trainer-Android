package com.ssjit.papertrading.data.remote

import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.LoginResponse
import com.ssjit.papertrading.data.models.search.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest):Response<LoginResponse>

    @GET("stock/search")
    suspend fun searchStocks(@Query("keyword") keyword:String):Response<SearchResponse>

}