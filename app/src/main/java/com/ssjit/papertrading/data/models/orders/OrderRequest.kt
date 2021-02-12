package com.ssjit.papertrading.data.models.orders

data class OrderRequest(
    val eachPrice: String,
    val noOfShares: String,
    val orderType: String,
    val price: String,
    val product: String,
    val stopLoss: String,
    val stoplossTrigger: String,
    val symbol: String,
    val target: String,
    val trailingStoploss: String,
    val triggeredPrice: String,
    val type: String,
    val userId: String,
    val validity: String,
    val variety: String
)