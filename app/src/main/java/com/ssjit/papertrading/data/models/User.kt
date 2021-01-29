package com.ssjit.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    val avatar: String,
    val email: String,

    @PrimaryKey
    val id: String,
    val last_login: Long,
    val name: String,
    val balance:String,
    val investment:String,
    val profit:String,
    val loss:String,
    val positive_transactions:Int,
    val negative_transactions:Int
)