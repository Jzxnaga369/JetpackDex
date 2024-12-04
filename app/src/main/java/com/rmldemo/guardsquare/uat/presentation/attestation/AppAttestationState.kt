package com.rmldemo.guardsquare.uat.presentation.attestation

import com.rmldemo.guardsquare.uat.domain.model.Token

data class AppAttestationState(
    val isLoading: Boolean = false,
    val token: Token = Token("", "", ""),
    val message: String = ""
)