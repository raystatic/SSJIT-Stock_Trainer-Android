package com.ssjit.papertrading.data.models.FNO

data class OptionsResponse(
    val error: Boolean,
    val options: List<OptionData>?=null,
    val message:String?=null
)