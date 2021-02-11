package com.ssjit.papertrading.data.remote

import com.ssjit.papertrading.data.models.FNO.FuturesResponse
import com.ssjit.papertrading.data.models.FNO.OptionsResponse
import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.LoginResponse
import com.ssjit.papertrading.data.models.news.NewsResponse
import com.ssjit.papertrading.data.models.search.SearchResponse
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import com.ssjit.papertrading.data.models.transaction.CreateOrderRequest
import com.ssjit.papertrading.data.models.transaction.CreateOrderResponse
import com.ssjit.papertrading.data.models.transaction.GetOrdersResponse
import com.ssjit.papertrading.data.models.watchlist.WatchlistResponse
import retrofit2.Response
import retrofit2.http.*

interface RapidApiService {

    @GET("news/list")
    suspend fun getNews(
            @Header("x-rapidapi-key") apiKey:String,
            @Header("x-rapidapi-host") apiHost:String,
            @Header("useQueryString") useQueryString:Boolean,
            @Query("category") category:String,
            @Query("region") region:String
    ):Response<NewsResponse>
}