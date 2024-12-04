package com.rmldemo.guardsquare.uat.presentation.notification

import com.rmldemo.guardsquare.uat.domain.model.Notification

data class NotificationState(
    val notifications: List<Notification> = listOf()
)
