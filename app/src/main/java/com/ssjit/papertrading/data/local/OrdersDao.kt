package com.ssjit.papertrading.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssjit.papertrading.data.models.transaction.Order

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order:Order)

    @Query("SELECT * FROM orders")
    fun getAllOrders():LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE status=:pending ORDER BY order_created_at DESC")
    fun getPendingOrders(pending:String):LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE status=:executed ORDER BY order_executed_at DESC")
    fun getExecutedOrders(executed:String):LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE status=:executed AND type=:buy ORDER BY order_created_at DESC")
    fun getHoldings(executed:String, buy:String):LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE status=:executed AND type=:sell ORDER BY order_executed_at DESC")
    fun getPositions(executed:String, sell:String):LiveData<List<Order>>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

}