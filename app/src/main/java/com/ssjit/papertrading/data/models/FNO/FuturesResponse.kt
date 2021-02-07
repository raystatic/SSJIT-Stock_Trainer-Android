package com.ssjit.papertrading.data.models.FNO

data class FuturesResponse(
    val error: Boolean,
    val futures: List<List<Future>>?=null,
    val message:String?=null
)