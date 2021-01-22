package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.local.LocalStocksDao
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class StockInfoRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val localStocksDao: LocalStocksDao
) {

    suspend fun getStockInfo(symbol:String) = apiHelper.getStockInfo(symbol)

    suspend fun upsertLocalStock(stockData: StockData) = localStocksDao.upsertLocalStock(stockData)
    fun getWatchlist() = localStocksDao.getWatchlistStocks()

}