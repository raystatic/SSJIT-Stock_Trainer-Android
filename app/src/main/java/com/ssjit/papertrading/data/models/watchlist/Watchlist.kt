package com.ssjit.papertrading.data.models.watchlist

data class Watchlist(
    val `data`: List<Data>,
    val futLink: String,
    val lastUpdateTime: String,
    val optLink: String,
    val otherSeries: List<String>,
    val tradedDate: String
)