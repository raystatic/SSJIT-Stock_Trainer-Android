package com.ssjit.papertrading.data.models.transaction

data class Order(
    val id: String,
    val intraday: Boolean,
    val no_shares: String,
    val order_amount: String,
    val order_created_at: String,
    val status: String,
    val symbol: String,
    val user_id: String
)