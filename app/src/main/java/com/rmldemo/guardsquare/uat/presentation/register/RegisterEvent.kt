package com.rmldemo.guardsquare.uat.presentation.register

import androidx.compose.ui.text.input.TextFieldValue

sealed class RegisterEvent {
    data object OnRegister: RegisterEvent()
    data class OnNameChange(val name: TextFieldValue): RegisterEvent()
    data class OnEmailChange(val email: TextFieldValue): RegisterEvent()
    data class OnPasswordChange(val password: TextFieldValue): RegisterEvent()
}