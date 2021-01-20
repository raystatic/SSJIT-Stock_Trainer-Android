package com.ssjit.papertrading.data.models.stockdetail

data class StockDetailResponse(
    val `data`: Quote?=null,
    val error: Boolean,
    val message:String?=null
)