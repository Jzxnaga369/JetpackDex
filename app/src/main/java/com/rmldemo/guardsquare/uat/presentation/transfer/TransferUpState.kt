package com.rmldemo.guardsquare.uat.presentation.transfer

import androidx.compose.ui.text.input.TextFieldValue

data class TransferUpState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
    val amount: TextFieldValue = TextFieldValue(""),
    val recipient: TextFieldValue = TextFieldValue(""),
)
