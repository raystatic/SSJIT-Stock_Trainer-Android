package com.ssjit.papertrading.data.models.FNO

data class OptionsResponse(
    val error: Boolean,
    val options: List<Option>?=null,
    val message:String?=null
)