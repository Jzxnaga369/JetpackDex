package com.rmldemo.guardsquare.uat.presentation.topup

import androidx.compose.ui.text.input.TextFieldValue

data class TopUpState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
    val amount: TextFieldValue = TextFieldValue(""),
)
