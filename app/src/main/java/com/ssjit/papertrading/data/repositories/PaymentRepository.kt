package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getPayment() = apiHelper.getPaymentIntent()

}