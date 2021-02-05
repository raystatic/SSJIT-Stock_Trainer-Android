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
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
        private val apiService: ApiService
):ApiHelper {

    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>
        = apiService.login(loginRequest)

    override suspend fun searchStock(keyword: String): Response<SearchResponse> {
        return apiService.searchStocks(keyword)
    }

    override suspend fun getStockInfo(symbol: String): Response<StockDetailResponse> {
        return apiService.getStockInfo(symbol)
    }

    override suspend fun createOrder(createOrderRequest: CreateOrderRequest): Response<CreateOrderResponse> {
        return apiService.createOrder(createOrderRequest)
    }

    override suspend fun getOrders(userId: String): Response<GetOrdersResponse> {
        return apiService.getOrders(userId)
    }

    override suspend fun getWatchlists(symbols: String): Response<WatchlistResponse> {
        return apiService.getWatchlists(symbols)
    }
}