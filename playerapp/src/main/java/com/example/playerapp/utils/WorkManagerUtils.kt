package com.example.playerapp.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.playerapp.workManager.FileDownloadWorker
import com.example.playerapp.ui.model.FileForDownload
import com.example.playerapp.ui.listeners.WorkManagerStatusListener
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.TimeUnit


object WorkManagerUtils {
    fun downloadFile(
        fileName: String,
        fileType: String,
        fileUrl: String,
        context: Context,
        result: (uri: Uri?, mimeType: String?) -> Unit
    ) {
        val mimeType = when (fileType) {
            "PDF" -> "application/pdf"
            "PNG" -> "image/png"
            "MP4" -> "video/mp4"
            "MP3" -> "audio/mpeg"
            else -> ""
        } // different types of files will have different mime type

        if (mimeType.isEmpty()) {
            result.invoke(null, null)
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/DownloaderDemo")
            }

            val resolver = context.contentResolver
            var uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if (uri != null) {
                cleanUpDuplicateFiles(context, uri, fileName)

                uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                if (uri != null) URL(fileUrl).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                else {
                    result.invoke(null, null)
                    return
                }
                result.invoke(uri, mimeType)
            } else {
                result.invoke(null, null)
            }
        } else {
            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            URL(fileUrl).openStream().use { input ->
                FileOutputStream(target).use { output ->
                    input.copyTo(output)
                }
            }

            result.invoke(target.toUri(), mimeType)
        }
    }

    private fun cleanUpDuplicateFiles(context: Context, uri: Uri, fileName: String) {
        getRealPathFromURI(context, uri)?.let { File(it) }?.let { file ->
            for (x in file.parentFile?.listFiles() ?: arrayOf<File>()) {
                if (x.nameWithoutExtension == fileName) {
                    x.delete()
                    break
                }
            }
            file.renameTo(File(file.parent, fileName))
        }
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Audio.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }

    fun startDownloadingFileWork(
        file: FileForDownload,
        context: Context,
        lifecycleOwner: LifecycleOwner,
        statusListener: WorkManagerStatusListener,
    ) {
        val data = Data.Builder()

        data.apply {
            putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, file.name)
            putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
            putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val uploadWorkRequest =
            OneTimeWorkRequestBuilder<FileDownloadWorker>()
                .setInputData(data.build())
                .setConstraints(constraints)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            file.name, ExistingWorkPolicy.REPLACE, uploadWorkRequest
        )

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(uploadWorkRequest.id)
            .observe(lifecycleOwner) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            statusListener.success(
                                it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI)
                                    ?: "",
                                it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_TYPE)
                                    ?: ""
                            )
                        }

                        WorkInfo.State.FAILED -> statusListener.failed("Failed!")
                        WorkInfo.State.RUNNING -> statusListener.running()
                        WorkInfo.State.ENQUEUED -> statusListener.enqueued("Started...")
                        WorkInfo.State.BLOCKED -> statusListener.blocked("Blocked!")
                        WorkInfo.State.CANCELLED -> statusListener.canceled("Canceled!")
                    }
                }
            }
    }
}