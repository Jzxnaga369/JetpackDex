package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class PostDeviceLogErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
)
