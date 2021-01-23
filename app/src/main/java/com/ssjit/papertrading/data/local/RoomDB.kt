package com.ssjit.papertrading.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssjit.papertrading.data.models.indices.BSE
import com.ssjit.papertrading.data.models.indices.NSE
import com.ssjit.papertrading.data.models.stockdetail.StockData

@Database(
    entities = [StockData::class, NSE::class, BSE::class],
    version = 1
)
abstract class RoomDB: RoomDatabase() {

    abstract fun getLocalStockDao():LocalStocksDao
    abstract fun getNSEDao():NSEDao
    abstract fun getBSEDao():BSEDao

}