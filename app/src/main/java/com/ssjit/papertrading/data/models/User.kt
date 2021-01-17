package com.ssjit.papertrading.data.models

data class User(
    val avatar: String,
    val email: String,
    val id: String,
    val last_login: Long,
    val name: String
)