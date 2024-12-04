package com.rmldemo.guardsquare.uat.presentation.about

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rmldemo.guardsquare.uat.domain.usecase.GetDeviceIdUseCase
import com.rmldemo.guardsquare.uat.presentation.about.AboutEvent
import com.rmldemo.guardsquare.uat.presentation.about.AboutState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    @ApplicationContext private val application: Context,
    private val getDeviceIdUseCase: GetDeviceIdUseCase
) : ViewModel() {
    var state by mutableStateOf(AboutState())

    init {
        onEvent(AboutEvent.GetAndroidId)
        onEvent(AboutEvent.GetSavedUUID)
    }

    private fun onEvent(event: AboutEvent) {
        when (event) {
            is AboutEvent.GetSavedUUID -> {
                getSavedUUID()
            }
            is AboutEvent.GetAndroidId -> {
                getAndroidId()
            }
        }
    }

    private fun getSavedUUID() {
        state = state.copy(
            savedUUID = getDeviceIdUseCase()
        )
    }



    @SuppressLint("HardwareIds")
    private fun getAndroidId() {
        state = state.copy(
            androidId = Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)
        )
    }
}