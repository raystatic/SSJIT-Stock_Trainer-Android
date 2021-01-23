package com.ssjit.papertrading.data.models.indices

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class NSEIndex(
    val `data`: NSEData,
    val error: Boolean,
    val type: String
){
    companion object{
        fun fromString(value:String?):NSEIndex{
            val type: Type = object : TypeToken<NSEIndex>() {}.type
            return Gson().fromJson(value, type)
        }
    }
}