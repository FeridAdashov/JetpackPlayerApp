package com.example.playerapp.workManager

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_SMALL
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.playerapp.R
import com.example.playerapp.utils.WorkManagerUtils

class FileDownloadWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val fileUrl = inputData.getString(FileParams.KEY_FILE_URL) ?: ""
        val fileName = inputData.getString(FileParams.KEY_FILE_NAME) ?: ""
        val fileType = inputData.getString(FileParams.KEY_FILE_TYPE) ?: ""

        Log.d("TAG", "doWork: $fileUrl | $fileName | $fileType")

        if (fileName.isEmpty()
            || fileType.isEmpty()
            || fileUrl.isEmpty()
        ) {
            Result.failure()
        }

        createNotification(buildDownloadingNotification())

        var uri: Uri? = null
        var mimeType: String? = null

        try {
            WorkManagerUtils.downloadFile(
                fileName = fileName,
                fileType = fileType,
                fileUrl = fileUrl,
                context = context
            ) { _uri, _mimeType ->
                uri = _uri
                mimeType = _mimeType
            }
        } catch (e: Exception) {
            e.printStackTrace()
            createNotification(buildResultNotification(false))
            return Result.failure()
        }

        return if (uri != null) {
            createNotification(buildResultNotification(true, uri.toString(), mimeType))

            Result.success(
                workDataOf(
                    FileParams.KEY_FILE_URI to uri.toString(),
                    FileParams.KEY_FILE_TYPE to mimeType
                )
            )
        } else {
            createNotification(buildResultNotification(false))
            Result.failure()
        }
    }

    private fun buildDownloadingNotification(): Notification {
        return NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(context.getString(R.string.downloading_your_file))
            .setOngoing(true)
            .setProgress(0, 0, true)
            .build()
    }

    private fun buildResultNotification(
        isSuccess: Boolean,
        fileUri: String? = null,
        fileType: String? = null
    ): Notification {
        val title = if (isSuccess) "Successfully downloaded" else "Download failed!"
        val actionName = context.getString(if (isSuccess) R.string.open_file else R.string.close)
        val actionDrawable = if (isSuccess) R.drawable.ic_open_with else R.drawable.ic_close
        val action =
            if (isSuccess) WorkManagerNotificationReceiver.ACTION_OPEN_FILE else WorkManagerNotificationReceiver.ACTION_CLOSE

        val actionIntent = Intent(context, WorkManagerNotificationReceiver::class.java).apply {
            putExtra(WorkManagerNotificationReceiver.ACTION, action)

            if (isSuccess) {
                putExtra(WorkManagerNotificationReceiver.FILE_URI, fileUri)
                putExtra(WorkManagerNotificationReceiver.FILE_TYPE, fileType)
            }
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        return NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(title)
            .addAction(actionDrawable, actionName, pendingIntent)
            .build()
    }

    private fun createNotification(notification: Notification) {
        val name = NotificationConstants.CHANNEL_NAME
        val description = NotificationConstants.CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(NotificationConstants.CHANNEL_ID, name, importance)
        channel.description = description

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context)
                .notify(
                    NotificationConstants.FILE_DOWNLOAD_WORK_MANAGER_NOTIFICATION_ID,
                    notification
                )
        }
    }

    object FileParams {
        internal const val KEY_FILE_URL = "key_file_url"
        internal const val KEY_FILE_TYPE = "key_file_type"
        internal const val KEY_FILE_NAME = "key_file_name"
        internal const val KEY_FILE_URI = "key_file_uri"
    }

    object NotificationConstants {
        internal const val CHANNEL_NAME = "download_file_worker_demo_channel"
        internal const val CHANNEL_DESCRIPTION = "download_file_worker_demo_description"
        internal const val CHANNEL_ID = "download_file_worker_demo_channel_123456"
        internal const val FILE_DOWNLOAD_WORK_MANAGER_NOTIFICATION_ID = 199
    }
}

