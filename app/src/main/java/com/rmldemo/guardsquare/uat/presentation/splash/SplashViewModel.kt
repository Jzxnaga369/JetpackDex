package com.rmldemo.guardsquare.uat.presentation.splash

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ap.zoloz.hummer.api.IZLZCallback
import com.ap.zoloz.hummer.api.ZLZConstants
import com.ap.zoloz.hummer.api.ZLZFacade
import com.ap.zoloz.hummer.api.ZLZRequest
import com.ap.zoloz.hummer.api.ZLZResponse
import com.rmldemo.guardsquare.uat.domain.model.DeviceLog
import com.rmldemo.guardsquare.uat.domain.usecase.GetDeviceIdUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.GetUserUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.GetVersionUseCase
import com.rmldemo.guardsquare.uat.domain.usecase.PostDeviceLogUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import com.rmldemo.guardsquare.uat.utils.TinkCryptoManager
import com.rmldemo.guardsquare.uat.utils.TinkKeyManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val application: Context,
    private val getUserUseCase: GetUserUseCase,
    private val getVersionUseCase: GetVersionUseCase,
    private val postDeviceLogUseCase: PostDeviceLogUseCase,
    private val getDeviceIdUseCase: GetDeviceIdUseCase,
) : ViewModel() {
    var state by mutableStateOf(SplashState())

    init {
        onEvent(SplashEvent.EncryptData)
        onEvent(SplashEvent.GetUser)
        onEvent(SplashEvent.GetVersion)
        onEvent(SplashEvent.StartZoloz)
        onEvent(SplashEvent.GetAndroidId)
        onEvent(SplashEvent.GetDeviceId)
    }

    fun onEvent(event: SplashEvent) {
        when(event) {
            is SplashEvent.GetUser -> {
                getUser()
            }
            is SplashEvent.GetVersion -> {
                getVersion()
            }
            is SplashEvent.PostDeviceLog -> {
                postDeviceLog(event.deviceLog)
            }
            is SplashEvent.EncryptData -> {
                encryptData()
            }
            is SplashEvent.StartZoloz -> {
                startZoloz()
            }
            is SplashEvent.GetAndroidId -> {
                getAndroidId()
            }
            is SplashEvent.GetDeviceId -> {
                getDeviceId()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { user ->
                                state = state.copy(
                                    isLoadingUser = false,
                                    user = user,
                                    firebase = "Initialize Firebase Done"
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingUser = false,
                                firebase = "Initialize Firebase Done"
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingUser = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun getVersion() {
        viewModelScope.launch {
            getVersionUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { version ->
                                state = state.copy(
                                    isLoadingVersion = false,
                                    version = "Get Api Verison Done"
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingVersion = false,
                                version = "Get Api Verison Failed"
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingVersion = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun postDeviceLog(deviceLog: DeviceLog) {
        viewModelScope.launch {
            postDeviceLogUseCase.invoke(deviceLog)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    isLoadingPostDeviceLog = false,
                                    deviceLog = "Post Device Log Done"
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoadingPostDeviceLog = false,
                                deviceLog = if (!result.message.isNullOrEmpty() && result.message.contains("500")) {
                                    result.message
                                } else {
                                    "Post Device Log Failed"
                                }
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingPostDeviceLog = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private fun encryptData() {
        // Crypto Tink
        val keysetHandle = TinkKeyManager.generateKeysetHandle()
        val plainText = "Sample App Jetpack Compose Using Crypto Tink"
        val encryptedData = TinkCryptoManager.encrypt(plainText, keysetHandle)
        TinkCryptoManager.decrypt(encryptedData, keysetHandle)
        state = state.copy(
            encryptionData = "Encryption Data Done"
        )
    }

    private fun startZoloz() {
        viewModelScope.launch {
            val zlzFacade: ZLZFacade = ZLZFacade.getInstance()
            val request = ZLZRequest()
            request.zlzConfig = "Client Config";
            request.bizConfig[ZLZConstants.CONTEXT] = this@SplashViewModel
            request.bizConfig[ZLZConstants.PUBLIC_KEY] = "RsaPubKey"
            request.bizConfig[ZLZConstants.CHAMELEON_CONFIG_PATH] = "config_realId.zip"
            request.bizConfig[ZLZConstants.LOCALE] = "id"
            zlzFacade.start(request, object : IZLZCallback {
                override fun onCompleted(response: ZLZResponse?) {}

                override fun onInterrupted(response: ZLZResponse?) {}
            })
            state = state.copy(
                startZoloz = "Start Zoloz Done"
            )
        }
    }

    @SuppressLint("HardwareIds")
    private fun getAndroidId() {
        state = state.copy(
            getAndroidId = Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)
        )
    }

    private fun getDeviceId() {
        state = state.copy(
            getDeviceId = getDeviceIdUseCase()
        )
    }
}