package com.kalfian.infokosan.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kalfian.infokosan.R
import com.kalfian.infokosan.modules.auth.LoginActivity
import com.kalfian.infokosan.modules.home.HomeActivity

class FcmService: FirebaseMessagingService() {
    val TAG = String::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "FROM ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            val msg: String = remoteMessage.data["message"].toString()
            Log.d("NotificationTest", msg)
            sendNotification(msg, "Infokosan")
        }

        Handler(Looper.getMainLooper()).postDelayed({
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }, 3000)
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_xl)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}