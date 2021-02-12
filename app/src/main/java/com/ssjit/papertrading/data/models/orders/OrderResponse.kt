package com.ssjit.papertrading.data.models.orders

data class OrderResponse(
    val error: Boolean,
    val order: Order,
    val message:String,
)