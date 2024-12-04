package com.rmldemo.guardsquare.uat.presentation.login

import androidx.compose.ui.text.input.TextFieldValue

sealed class LoginEvent {
    data object OnLogin: LoginEvent()
    data class OnEmailChange(val email: TextFieldValue): LoginEvent()
    data class OnPasswordChange(val password: TextFieldValue): LoginEvent()
}