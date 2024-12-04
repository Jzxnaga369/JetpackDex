package com.rmldemo.guardsquare.uat.presentation.payment

sealed class PaymentEvent {
    data object  OnPayment: PaymentEvent()
}