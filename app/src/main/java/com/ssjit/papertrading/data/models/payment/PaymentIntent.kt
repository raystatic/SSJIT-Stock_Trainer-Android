package com.ssjit.papertrading.data.models.payment

data class PaymentIntent(
    val error:Boolean,
    val client_secret:String
)