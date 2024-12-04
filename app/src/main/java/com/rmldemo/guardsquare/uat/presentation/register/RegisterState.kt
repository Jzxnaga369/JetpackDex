package com.rmldemo.guardsquare.uat.presentation.register

import androidx.compose.ui.text.input.TextFieldValue

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
    val name: TextFieldValue = TextFieldValue(""),
    val email: TextFieldValue = TextFieldValue(""),
    val password:  TextFieldValue = TextFieldValue("")
)
