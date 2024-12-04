package com.rmldemo.guardsquare.uat.presentation.splash

import com.rmldemo.guardsquare.uat.domain.model.DeviceLog

sealed class SplashEvent {
    data object GetUser: SplashEvent()
    data object GetVersion: SplashEvent()
    data class PostDeviceLog(val deviceLog: DeviceLog): SplashEvent()
    data object EncryptData: SplashEvent()
    data object StartZoloz: SplashEvent()
    data object GetAndroidId: SplashEvent()
    data object GetDeviceId: SplashEvent()
}