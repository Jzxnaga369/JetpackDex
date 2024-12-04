package com.rmldemo.guardsquare.uat.domain.repostory

import com.rmldemo.guardsquare.uat.domain.model.DeviceLog
import com.rmldemo.guardsquare.uat.domain.model.Notification
import com.rmldemo.guardsquare.uat.domain.model.Promo
import com.rmldemo.guardsquare.uat.domain.model.Token
import com.rmldemo.guardsquare.uat.domain.model.Version
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow

interface InformationRepository {
    fun getPromos(): Flow<Resource<List<Promo>>>
    fun getVersion(): Flow<Resource<Version>>
    fun getNotifications(): Flow<List<Notification>>
    fun insertNotification(notification: Notification)
    fun postDeviceLog(deviceLog: DeviceLog): Flow<Resource<DeviceLog>>
    fun getDeviceId(): String
    fun checkToken(): Flow<Resource<Token>>
}