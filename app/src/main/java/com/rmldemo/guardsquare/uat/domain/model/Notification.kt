package com.rmldemo.guardsquare.uat.domain.model

data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val sentTime: Long,
)
