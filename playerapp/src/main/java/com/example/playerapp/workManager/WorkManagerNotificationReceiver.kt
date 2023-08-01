package com.example.playerapp.workManager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.example.playerapp.workManager.FileDownloadWorker.NotificationConstants.FILE_DOWNLOAD_WORK_MANAGER_NOTIFICATION_ID


class WorkManagerNotificationReceiver : BroadcastReceiver() {
    companion object {
        internal const val ACTION = "ACTION_NAME"
        internal const val ACTION_CLOSE = "ACTION_CLOSE"
        internal const val ACTION_OPEN_FILE = "ACTION_OPEN_FILE"
        internal const val FILE_URI = "FILE_URI"
        internal const val FILE_TYPE = "FILE_TYPE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (intent?.getStringExtra(ACTION) == ACTION_CLOSE) {
                notificationManager.cancel(FILE_DOWNLOAD_WORK_MANAGER_NOTIFICATION_ID)
            } else if (intent?.getStringExtra(ACTION) == ACTION_OPEN_FILE) {
                notificationManager.cancel(FILE_DOWNLOAD_WORK_MANAGER_NOTIFICATION_ID)

                intent.getStringExtra(FILE_URI)?.let { uri ->
                    val i = Intent(Intent.ACTION_VIEW)
                    i.setDataAndType(uri.toUri(), intent.getStringExtra(FILE_TYPE))
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    try {
                        context.startActivity(i)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Can't open file", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}