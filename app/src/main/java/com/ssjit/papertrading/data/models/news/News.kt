package com.ssjit.papertrading.data.models.news

data class News(
        val uuid:String,
        val title:String,
        val link:String,
        val summary:String,
        val author:String,
        val content:String,
        val published_at:String
)