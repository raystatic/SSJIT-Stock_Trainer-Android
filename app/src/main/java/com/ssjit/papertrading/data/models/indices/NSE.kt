package com.ssjit.papertrading.data.models.indices

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NSE")
data class NSE(
    val high: String,

    @PrimaryKey
    val indexName: String,
    val indexOrder: String,
    val last: String,
    val low: String,
    val `open`: String,
    val percChange: String,
    val previousClose: String,
    val timeVal: String,
    val yearHigh: String,
    val yearLow: String
)