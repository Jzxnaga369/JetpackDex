package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class CheckTokenResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: TokenResponse,
)
