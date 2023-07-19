package com.rcudev.player_service.service

import android.content.Context
import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.rcudev.player_service.service.notification.MediaNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MediaPlayerService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: MediaNotificationManager


    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = mediaSession
        )

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        mediaSession.player.release()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession

    companion object {
        private var isServiceRunning = false

        fun start(context: Context) {
            if (!isServiceRunning) {
                val intent = Intent(context, MediaPlayerService::class.java)
                context.startForegroundService(intent)
                isServiceRunning = true
            }
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, MediaPlayerService::class.java))
            isServiceRunning = false
        }
    }
}