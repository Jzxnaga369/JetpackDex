package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class PostDeviceLogResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("data")
    val data: DeviceLogBody
)
