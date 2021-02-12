package com.ssjit.papertrading.data.models.orders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    val eachPrice: String,

    @PrimaryKey
    val id: String,
    val noOfShares: String,
    val orderType: String,
    val price: String,
    val product: String,
    val status: String,
    val stopLoss: String,
    val stoplossTrigger: String,
    val symbol: String,
    val target: String,
    val trailingStoploss: String,
    val triggeredPrice: String,
    val type: String,
    val userId: String,
    val validity: String,
    val variety: String,
    val companyName:String?=null,
    val currentPrice:String?=null,
    val order_executed_at:String?=null,
    val order_created_at:String?=null
)