package com.rmldemo.guardsquare.uat.presentation.about

sealed class AboutEvent {
    data object GetAndroidId: AboutEvent()
    data object GetSavedUUID: AboutEvent()
}