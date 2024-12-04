package com.rmldemo.guardsquare.uat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val title: String,
    val message: String,
    val sentTime: Long,
)