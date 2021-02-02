package com.ssjit.papertrading.data.models.transaction

data class GetOrdersResponse(
    val error:Boolean,
    val orders:List<Order>?,
    val message:String?
)