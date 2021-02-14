package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.local.UserDao
import com.ssjit.papertrading.data.models.User
import com.ssjit.papertrading.data.models.payment.PaymentRequest
import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao
) {

    suspend fun getPayment() = apiHelper.getPaymentIntent()

    suspend fun createPayment(paymentRequest: PaymentRequest) = apiHelper.createPayment(paymentRequest)

    suspend fun insertUser(user:User) = userDao.insertUser(user)

    fun getUser() = userDao.getUsers()

}