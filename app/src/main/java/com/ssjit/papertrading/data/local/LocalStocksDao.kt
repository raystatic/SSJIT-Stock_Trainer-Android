package com.ssjit.papertrading.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ssjit.papertrading.data.models.stockdetail.StockData
import java.io.Flushable

@Dao
interface LocalStocksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLocalStock(stockData: StockData)

    @Query("DELETE from localStocks WHERE symbol=:symbol")
    suspend fun deleteBySymbol(symbol:String)

    @Query("SELECT * from localStocks WHERE symbol=:symbol")
    fun getStockBySymbol(symbol:String):LiveData<List<StockData>>

    @Query("DELETE from localStocks")
    suspend fun deleteAllLocalStocks()

    @Query("SELECT * from localStocks")
    fun getAllLocalStocks():LiveData<List<StockData>>

    @Query("SELECT * from localStocks WHERE addedToWatchList is 1")
    fun getWatchlistStocks():LiveData<List<StockData>>

    @Query("SELECT * FROM localStocks WHERE symbol=:symbol AND addedToWatchList is 1")
    fun isStockInWatchlist(symbol: String):LiveData<StockData>

}