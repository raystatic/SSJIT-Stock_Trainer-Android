package com.ssjit.papertrading.data.models.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(

    @PrimaryKey
    val id: String,
    val intraday: Int,
    val no_shares: String,
    val order_amount: String,
    val order_created_at: String,
    val status: String,
    val symbol: String,
    val user_id: String
)