package com.ssjit.papertrading.data.models.indices

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class BSEIndex(
    val `data`: List<BSE>,
    val error: Boolean,
    val type: String
){
    companion object{
        fun fromString(value:String?):BSEIndex{
            val type: Type = object : TypeToken<BSEIndex>() {}.type
            return Gson().fromJson(value, type)
        }
    }
}