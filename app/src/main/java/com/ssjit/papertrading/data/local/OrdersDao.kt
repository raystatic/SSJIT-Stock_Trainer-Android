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

    @Query("SELECT * FROM orders WHERE status=:pending")
    fun getPendingOrders(pending:String):LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE status=:executed")
    fun getExecutedOrders(executed:String):LiveData<List<Order>>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

}