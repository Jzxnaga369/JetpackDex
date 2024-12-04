package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("protocolError_name")
    val protocolErrorName: String,
    @SerializedName("effect_name")
    val effectName: String,
    @SerializedName("date_expired_jwt")
    val dateExpiredJwt: String
)
