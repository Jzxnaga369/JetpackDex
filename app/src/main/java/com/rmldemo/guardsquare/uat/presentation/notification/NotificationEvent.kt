package com.rmldemo.guardsquare.uat.presentation.notification

sealed class NotificationEvent {
    data object GetNotifications: NotificationEvent()
}