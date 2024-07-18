package com.interenting.payload

data class BookingPaymentDetails(
    val bId: Long,
    val amount: Long,
    val senderAccId: String,
    val receiverAccId: String,
)
