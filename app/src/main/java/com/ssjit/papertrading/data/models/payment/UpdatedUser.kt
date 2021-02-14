package com.ssjit.papertrading.data.models.payment

import com.ssjit.papertrading.data.models.User

data class UpdatedUser(
    val error:Boolean,
    //val user: User?=null,
    val message:String?=null
)