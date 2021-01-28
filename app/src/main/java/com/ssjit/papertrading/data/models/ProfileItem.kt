package com.ssjit.papertrading.data.models

data class ProfileItem(
        val itemType:String,
        val text:String?=null,
        val image:Int?=null,
        val caption:String
)