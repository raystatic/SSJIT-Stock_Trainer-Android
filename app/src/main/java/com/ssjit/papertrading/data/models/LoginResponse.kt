package com.ssjit.papertrading.data.models

data class LoginResponse(
    val error: Boolean,
    val user: User?=null,
    val message:String?=null
)