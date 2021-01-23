package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.local.BSEDao
import com.ssjit.papertrading.data.local.LocalStocksDao
import com.ssjit.papertrading.data.local.NSEDao
import com.ssjit.papertrading.data.models.indices.BSE
import com.ssjit.papertrading.data.models.indices.NSE
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class StockInfoRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val localStocksDao: LocalStocksDao,
    private val nseDao: NSEDao,
    private val bseDao: BSEDao
) {

    suspend fun getStockInfo(symbol:String) = apiHelper.getStockInfo(symbol)

    suspend fun upsertLocalStock(stockData: StockData) = localStocksDao.upsertLocalStock(stockData)
    fun getWatchlist() = localStocksDao.getWatchlistStocks()

    suspend fun upsertNSE(nse: NSE)=  nseDao.upsertNSE(nse)
    suspend fun upsertBSE(bse: BSE) = bseDao.upsertBSE(bse)

    fun getAllNSE() = nseDao.getAllNSE()
    fun getAllBSE() = bseDao.getAllBSE()

    fun getNSEByIndexName(name:String) = nseDao.getIndexByName(name)
    fun getBSEBySecurityCode(code:String) = bseDao.getBSEBySecurityCode(code)

    suspend fun deleteAllNSE() = nseDao.deleteAllNSE()
    suspend fun deleteAllBSE() = bseDao.deleteAllBSE()

}