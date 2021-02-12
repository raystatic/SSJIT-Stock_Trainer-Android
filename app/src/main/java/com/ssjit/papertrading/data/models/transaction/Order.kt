package com.ssjit.papertrading.data.models.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Order(

    val id: String,
    val intraday: Int,
    val no_shares: String,
    val order_amount: String,
    val order_created_at: String,
    val order_executed_at:String?=null,
    val status: String,
    val symbol: String,
    val user_id: String,
    val type:String,
    val currentPrice:Float?=null,
    val companyName:String?=null
)