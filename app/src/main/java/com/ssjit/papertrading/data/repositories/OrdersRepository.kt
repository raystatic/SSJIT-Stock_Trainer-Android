package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.local.OrdersDao
import com.ssjit.papertrading.data.local.UserDao
import com.ssjit.papertrading.data.models.transaction.Order
import com.ssjit.papertrading.data.remote.ApiHelper
import com.ssjit.papertrading.other.Constants
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val ordersDao: OrdersDao,
    private val apiHelper:ApiHelper,
    private val userDao: UserDao
) {

    suspend fun getStockInfo(symbol:String) = apiHelper.getStockInfo(symbol)

    suspend fun getOrders(userId:String) = apiHelper.getOrders(userId)

    suspend fun insertOrders(order:Order) = ordersDao.insertOrder(order)

    fun getPendingOrders() = ordersDao.getPendingOrders(Constants.PENDING)

    fun getExecutedOrders() = ordersDao.getExecutedOrders(Constants.EXECUTED)

    fun getAllOrders() = ordersDao.getAllOrders()

    suspend fun deleteAllOrders() = ordersDao.deleteAllOrders()

    fun getHoldings() = ordersDao.getHoldings(Constants.EXECUTED, Constants.BUY)

    fun getPositions() = ordersDao.getPositions(Constants.EXECUTED, Constants.BUY)

    fun getUser() = userDao.getUsers()

}