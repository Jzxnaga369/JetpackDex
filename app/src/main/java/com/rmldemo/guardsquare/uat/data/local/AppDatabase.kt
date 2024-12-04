package com.rmldemo.guardsquare.uat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NotificationEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val notificationDao: NotificationDao

    companion object {
        const val DATABASE_NAME = "notification_db"
    }
}