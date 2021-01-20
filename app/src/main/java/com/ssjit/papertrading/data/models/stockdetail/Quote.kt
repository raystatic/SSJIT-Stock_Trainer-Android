package com.ssjit.papertrading.data.models.stockdetail

data class Quote(
    val `data`: List<StockData>?=null,
    val futLink: String,
    val lastUpdateTime: String,
    val optLink: String,
    val otherSeries: List<String>,
    val tradedDate: String
)