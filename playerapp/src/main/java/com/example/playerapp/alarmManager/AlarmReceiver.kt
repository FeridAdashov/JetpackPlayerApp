package com.example.playerapp.alarmManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import com.example.playerapp.R
import com.rcudev.player_service.service.PlayerEvent
import com.rcudev.player_service.service.SimpleMediaServiceHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val NOTIFICATION_ID = 456
        private const val CHANNEL_ID = "456"
        private const val ACTION = "ACTION_NAME"
        private const val ACTION_CLOSE = "ACTION_CLOSE"
        const val MESSAGE = "MESSAGE"
        const val MUSIC_URL = "MUSIC_URL"
    }

    @Inject
    lateinit var simpleMediaServiceHandler: SimpleMediaServiceHandler

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(MESSAGE)
        val musicUrl = intent?.getStringExtra(MUSIC_URL)
        println("Alarm triggered: $message   ---   $musicUrl")

        musicUrl?.let {
            simpleMediaServiceHandler.addMediaItem(MediaItem.fromUri(musicUrl))
            CoroutineScope(Dispatchers.Main).launch {
                simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Play)
            }
        }

        context?.let {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (intent?.getStringExtra(ACTION) == ACTION_CLOSE) {
                CoroutineScope(Dispatchers.Main).launch {
                    simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
                }
                notificationManager.cancel(NOTIFICATION_ID)
            } else if (intent?.getStringExtra(ACTION) == null) {
                val closeIntent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra(ACTION, ACTION_CLOSE)
                }
                val closePendingIntent: PendingIntent =
                    PendingIntent.getBroadcast(
                        context,
                        0,
                        closeIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_clock)
                    .setContentTitle("Music Alarm")
                    .setContentText(message)
                    .setOngoing(true)
                    .addAction(R.drawable.ic_close, "Close", closePendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, "Alarm", importance).apply {
                    description = "Music alarm channel"
                    setSound(null, null)//Because we will play custom music from url
                }

                notificationManager.createNotificationChannel(channel)
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }
}