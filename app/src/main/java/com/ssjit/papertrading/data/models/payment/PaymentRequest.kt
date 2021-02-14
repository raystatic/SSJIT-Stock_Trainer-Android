package com.ssjit.papertrading.data.models.payment

data class PaymentRequest(
    val userId:String,
    val amount:String,
    val created_at:String,
    val status:String
)