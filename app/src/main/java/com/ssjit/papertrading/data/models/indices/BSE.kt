package com.ssjit.papertrading.data.models.indices

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BSE")
data class BSE(

    @PrimaryKey
    val key: String,
    val pointChange: String,
    val pointPercent: String,
    val securityCode: String,
    val todayClose: String
)