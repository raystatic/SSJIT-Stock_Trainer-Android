package com.ssjit.papertrading.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssjit.papertrading.data.models.stockdetail.StockData

@Database(
    entities = [StockData::class],
    version = 1
)
abstract class RoomDB: RoomDatabase() {

    abstract fun getLocalStockDao():LocalStocksDao

}