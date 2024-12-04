package com.rmldemo.guardsquare.uat.presentation.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
    val email: TextFieldValue = TextFieldValue("user@example.com"),
    val password:  TextFieldValue = TextFieldValue("password")
)
