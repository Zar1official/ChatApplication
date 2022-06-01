package ru.zar1official.chatapplication.data.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.zar1official.chatapplication.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomNotificationManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "message_channel"
    private val channelName = "message_name"

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
    }

    fun notifyUser(title: String, text: String, id: Int) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_background)

        manager.notify(id, builder.build())
    }
}