package com.ssjit.papertrading.data.models.transaction

import com.ssjit.papertrading.data.models.orders.Order

data class GetOrdersResponse(
    val error:Boolean,
    val orders:List<Order>?,
    val message:String?
)