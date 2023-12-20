package com.team2.todo.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.team2.todo.R
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random


/**
 * Created by Manu KJ on 11/2/23.
 */

@SuppressLint("StaticFieldLeak")
object NotificationUtil {
    private lateinit var context: Context
    private lateinit var notificationManager: NotificationManager
    private const val LOCATION_CHANNEL_ID = "LOCATION_CHANNEL_ID"
    const val LOCATION_CHANNEL_NAME = "LOCATION_CHANNEL_NAME"


    fun init(context: Context) {
        this.context = context
        notificationManager = context.getSystemService(NotificationManager::class.java)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channelName = "Notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(LOCATION_CHANNEL_ID, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    fun showGeoFencingNotification(property: TodoWithSubTodos) {
        var message =
            "There are ${
                getPendingSubTask(
                    property
                )
            } pending task on this property"
        val notification = NotificationCompat.Builder(context, LOCATION_CHANNEL_ID)
            .setContentTitle("You're near the ${property.todo.title} property")
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setSmallIcon(R.drawable.ic_logo)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )

    }

    private fun getPendingSubTask(property: TodoWithSubTodos): String {
        var count = 1;
        property.subtodos.forEach {
            if (it.status == false) {
                count++
            }
        }
        return count.toString()
    }

}
