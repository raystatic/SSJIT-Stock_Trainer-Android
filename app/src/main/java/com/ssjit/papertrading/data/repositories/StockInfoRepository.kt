package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class StockInfoRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getStockInfo(symbol:String) = apiHelper.getStockInfo(symbol)

}