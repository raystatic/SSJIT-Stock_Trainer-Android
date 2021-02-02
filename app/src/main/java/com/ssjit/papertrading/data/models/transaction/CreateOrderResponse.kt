package com.ssjit.papertrading.data.models.transaction

data class CreateOrderResponse(
    val error: Boolean,
    val order: Order?,
    val message:String?
)