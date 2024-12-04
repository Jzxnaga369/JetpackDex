package com.rmldemo.guardsquare.uat.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rmldemo.guardsquare.uat.domain.model.Notification
import com.rmldemo.guardsquare.uat.domain.repostory.InformationRepository
import com.rmldemo.guardsquare.uat.presentation.MainActivity
import com.rmldemo.guardsquare.uat.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService: FirebaseMessagingService() {

    @Inject
    lateinit var repository: InformationRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
        val notificationId = Random().nextInt(300)

        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = remoteMessage.notification?.title ?: ""
        val message = remoteMessage.notification?.body  ?: ""
        val time = Calendar.getInstance().timeInMillis

        repository.insertNotification(
            Notification(
                id = time + Random().nextLong(),
                sentTime = time,
                title = title,
                message = message
            )
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "CHANNEL DEFAULT",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}