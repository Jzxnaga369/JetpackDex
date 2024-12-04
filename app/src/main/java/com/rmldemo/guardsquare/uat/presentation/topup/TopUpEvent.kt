package com.rmldemo.guardsquare.uat.presentation.topup

import androidx.compose.ui.text.input.TextFieldValue

sealed class TopUpEvent {
    data object OnTopUp: TopUpEvent()
    data class OnAmountChange(val amount: TextFieldValue): TopUpEvent()
}