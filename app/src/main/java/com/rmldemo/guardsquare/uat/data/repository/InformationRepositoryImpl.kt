package com.rmldemo.guardsquare.uat.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rmldemo.guardsquare.uat.data.local.NotificationDao
import com.rmldemo.guardsquare.uat.data.local.NotificationEntity
import com.rmldemo.guardsquare.uat.data.remote.ApiService
import com.rmldemo.guardsquare.uat.data.remote.CheckTokenBody
import com.rmldemo.guardsquare.uat.data.remote.DeviceLogBody
import com.rmldemo.guardsquare.uat.data.remote.PostDeviceLogErrorResponse
import com.rmldemo.guardsquare.uat.domain.model.DeviceLog
import com.rmldemo.guardsquare.uat.domain.model.Notification
import com.rmldemo.guardsquare.uat.domain.model.Promo
import com.rmldemo.guardsquare.uat.domain.model.Token
import com.rmldemo.guardsquare.uat.domain.model.Version
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.utils.Constans.DEVICE_ID
import com.rmldemo.guardsquare.uat.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class InformationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val notificationDao: NotificationDao,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) : InformationRepository {
    override fun getPromos(): Flow<Resource<List<Promo>>> = flow {
        emit(Resource.Loading())
        try {
            val result = apiService.getPromos()
            emit(Resource.Success(result.map {
                Promo(
                    id = it.id,
                    name = it.name,
                    url = it.url,
                    createdAt = it.createdAt,
                )
            }))
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun getVersion(): Flow<Resource<Version>> = flow {
        emit(Resource.Loading())
        try {
            val result = apiService.getVersion()
            emit(
                Resource.Success(result.data
                .filter { it.app.uppercase() == "ANDROID" }
                .map {
                    Version(
                        id = it.id,
                        createdAt = it.createdDate,
                        app = it.app,
                        version = it.version,
                    )
                }.first())
            )
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun getNotifications(): Flow<List<Notification>> = notificationDao.getNotifications().map {
        it.map {
            Notification(
                id = it.id,
                title = it.title,
                message = it.message,
                sentTime = it.sentTime
            )
        }
    }

    override fun insertNotification(notification: Notification) {
        val executorService: ExecutorService = Executors.newSingleThreadScheduledExecutor()
        executorService.execute {
            notificationDao.insertNotification(
                NotificationEntity(
                    id = notification.id,
                    title = notification.title,
                    message = notification.message,
                    sentTime = notification.sentTime,
                )
            )
        }
    }

    override fun postDeviceLog(deviceLog: DeviceLog): Flow<Resource<DeviceLog>> = flow {
        emit(Resource.Loading())
        try {
            val result = apiService.postDeviceLog(deviceLogBody = DeviceLogBody(
                deviceName = deviceLog.deviceName,
                systemName = deviceLog.systemName,
                systemVersion = deviceLog.systemVersion,
                model = deviceLog.model,
                deviceModel = deviceLog.deviceModel,
                locationModel = deviceLog.locationModel,
                uuid = deviceLog.uuid,
                deviceId = deviceLog.deviceId,
                currentAddress = deviceLog.currentAddress,
                createdAt = deviceLog.createdAt,
                token = deviceLog.token
            ))
            emit(
                Resource.Success(DeviceLog(
                deviceName = result.data.deviceName,
                systemName = result.data.systemName,
                systemVersion = result.data.systemVersion,
                model = result.data.model,
                deviceModel = result.data.deviceModel,
                locationModel = result.data.locationModel,
                uuid = result.data.uuid,
                deviceId = result.data.deviceId,
                currentAddress = result.data.currentAddress,
                createdAt = deviceLog.createdAt,
                token = "Token"
            )))
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                is HttpException -> {
                    val errorMessageResponseType = object : TypeToken<PostDeviceLogErrorResponse>() {}.type
                    val error: PostDeviceLogErrorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), errorMessageResponseType)
                    emit(Resource.Error("Error Code : " + error.code.toString() + "\n" + error.message))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun getDeviceId(): String {
        return sharedPreferences.getString(DEVICE_ID, "") ?: ""
    }

    override fun checkToken(): Flow<Resource<Token>> = flow {
        emit(Resource.Loading())
        try {
//            val token = AppAttestation.getToken(context)
            val token = "Token"
            val result = apiService.checkToken(
                checkTokenBody = CheckTokenBody(token)
            )
            emit(
                Resource.Success(
                    Token(
                        protocolErrorName = result.data.protocolErrorName,
                        effectName = result.data.effectName,
                        dateExpiredJwt = result.data.dateExpiredJwt,
                    )
                )
            )
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }
}