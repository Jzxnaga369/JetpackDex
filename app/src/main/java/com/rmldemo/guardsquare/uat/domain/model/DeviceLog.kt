package com.rmldemo.guardsquare.uat.domain.model

data class DeviceLog(
    val deviceName: String,
    val systemName: String,
    val systemVersion: String,
    val model: String,
    val deviceModel: String,
    val locationModel: String,
    val uuid: String,
    val deviceId: String,
    val currentAddress: String,
    val createdAt: String,
    val token: String
)