package com.ssjit.papertrading.data.models.transaction

data class CreateOrderRequest(
    val symbol:String,
    val noOfShares:String,
    val orderCreatedAt:String,
    val userId:String,
    val orderAmount:String,
    val intraday:Boolean
)