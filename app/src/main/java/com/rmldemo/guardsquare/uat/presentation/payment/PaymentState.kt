package com.rmldemo.guardsquare.uat.presentation.payment

import com.rmldemo.guardsquare.uat.domain.model.Service

data class PaymentState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
    val service: Service = Service(
        id = "",
        name = "",
        imageUrl = "",
        amount = 0
    )
)
