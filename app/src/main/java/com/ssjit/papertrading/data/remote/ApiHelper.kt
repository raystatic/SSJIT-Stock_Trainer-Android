package com.ssjit.papertrading.data.remote

import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.LoginResponse
import com.ssjit.papertrading.data.models.search.SearchResponse
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import com.ssjit.papertrading.data.models.transaction.CreateOrderRequest
import com.ssjit.papertrading.data.models.transaction.CreateOrderResponse
import com.ssjit.papertrading.data.models.transaction.GetOrdersResponse
import com.ssjit.papertrading.data.models.watchlist.WatchlistResponse
import retrofit2.Response
import retrofit2.http.POST

interface ApiHelper {

    suspend fun login(loginRequest: LoginRequest):Response<LoginResponse>

    suspend fun searchStock(keyword:String):Response<SearchResponse>

    suspend fun getStockInfo(symbol:String):Response<StockDetailResponse>

    suspend fun createOrder(createOrderRequest: CreateOrderRequest):Response<CreateOrderResponse>

    suspend fun getOrders(userId:String):Response<GetOrdersResponse>

    suspend fun getWatchlists(symbols:String):Response<WatchlistResponse>

}