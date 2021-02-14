package com.ssjit.papertrading.data.remote

import com.ssjit.papertrading.data.models.FNO.FuturesResponse
import com.ssjit.papertrading.data.models.FNO.OptionsResponse
import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.LoginResponse
import com.ssjit.papertrading.data.models.orders.OrderRequest
import com.ssjit.papertrading.data.models.orders.OrderResponse
import com.ssjit.papertrading.data.models.payment.PaymentIntent
import com.ssjit.papertrading.data.models.payment.PaymentRequest
import com.ssjit.papertrading.data.models.payment.UpdatedUser
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

    override suspend fun getOptions(symbol: String): Response<OptionsResponse> {
        return apiService.getOptions(symbol)
    }

    override suspend fun getFutures(symbol: String): Response<FuturesResponse> {
        return apiService.getFutures(symbol)
    }

    override suspend fun createOrderRequest(createOrderRequest: OrderRequest): Response<OrderResponse> {
        return apiService.createOrderRequest(createOrderRequest)
    }

    override suspend fun getPaymentIntent(): Response<PaymentIntent> {
        return apiService.getPaymentIntent()
    }

    override suspend fun createPayment(paymentRequest: PaymentRequest): Response<UpdatedUser> {
        return apiService.createPayment(paymentRequest)
    }
}