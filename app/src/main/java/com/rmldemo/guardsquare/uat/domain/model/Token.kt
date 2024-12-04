package com.rmldemo.guardsquare.uat.domain.model

data class Token(
    val protocolErrorName: String,
    val effectName: String,
    val dateExpiredJwt: String
)
