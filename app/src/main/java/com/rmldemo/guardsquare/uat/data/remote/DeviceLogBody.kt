package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class DeviceLogBody(
    @SerializedName("device_name")
    val deviceName: String,
    @SerializedName("system_name")
    val systemName: String,
    @SerializedName("system_version")
    val systemVersion: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("device_model")
    val deviceModel: String,
    @SerializedName("location_model")
    val locationModel: String,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("current_address")
    val currentAddress: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("token")
    val token: String,
)