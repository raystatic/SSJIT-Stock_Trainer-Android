package com.ssjit.papertrading.data.models.search

data class SearchResponse(
    val `data`: List<Data>?=null,
    val error: Boolean,
    val message:String?=null
)