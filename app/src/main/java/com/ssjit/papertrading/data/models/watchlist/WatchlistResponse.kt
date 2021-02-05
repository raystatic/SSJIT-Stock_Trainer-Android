package com.ssjit.papertrading.data.models.watchlist

data class WatchlistResponse(
    val error: Boolean,
    val watchlist: List<Watchlist>
)