package com.rmldemo.guardsquare.uat.presentation.splash

import com.rmldemo.guardsquare.uat.domain.model.User

data class SplashState(
    val isLoadingUser: Boolean = false,
    val isLoadingVersion: Boolean = false,
    val isLoadingPostDeviceLog: Boolean = false,
    val user: User = User("", "", "", "", 0),
    val firebase: String = "Loading Initialize Firebase",
    val version: String = "Loading Api Version",
    val deviceLog: String = "Loading Post Device Log",
    val encryptionData: String = "Loading Encryption Data",
    val startZoloz: String = "Loading Start Zoloz",
    val getAndroidId: String = "Loading Get Android Id",
    val getDeviceId: String = "Loading Get Device Id"
)