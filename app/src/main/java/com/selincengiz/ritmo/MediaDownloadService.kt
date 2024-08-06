package com.selincengiz.ritmo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.scheduler.Scheduler
import com.selincengiz.ritmo.util.DownloadUtil
import java.lang.Exception

@OptIn(UnstableApi::class)
class MediaDownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    DOWNLOAD_NOTIFICATION_CHANNEL_ID,
    R.string.download_notification_channel_name,
    R.string.download_notification_channel_description
) {

    private lateinit var downloadManager: DownloadManager

    override fun onCreate() {
        super.onCreate()
        Log.i("Notification", "Service onCreate called")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                DOWNLOAD_NOTIFICATION_CHANNEL_ID,
                getString(R.string.download_notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.download_notification_channel_description)
            }
            notificationManager.createNotificationChannel(channel)
            Log.i("Notification", "Notification channel created")
        }

        val notification = getForegroundNotification(emptyList(), 0)
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
        Log.i("Notification", "Foreground notification started")
    }

    override fun getDownloadManager(): DownloadManager {
        downloadManager = DownloadUtil.getDownloadManager(this)
        downloadManager.addListener(object : DownloadManager.Listener {
            override fun onDownloadChanged(
                downloadManager: DownloadManager,
                download: Download,
                finalException: Exception?
            ) {
                Log.i("listener percent", download.percentDownloaded.toString())
                Log.i("listener state ", download.state.toString())
                Log.i("listener uri ", download.request.uri.toString())
                Log.i("listener exception", finalException?.message.toString())

                val notification = getForegroundNotification(listOf(download), 0)
                startForeground(FOREGROUND_NOTIFICATION_ID, notification)
            }
        })
        return downloadManager
    }

    override fun getScheduler(): Scheduler {
        return PlatformScheduler(this, JOB_ID)
    }

    override fun getForegroundNotification(
        downloads: List<Download>,
        notMetRequirements: Int
    ): Notification {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DOWNLOAD_NOTIFICATION_CHANNEL_ID,
                getString(R.string.download_notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.download_notification_channel_description)
            }
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(getString(R.string.download_notification_title))
            .setContentText(getString(R.string.download_notification_text))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)

        return builder.build()
    }


    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 1
        const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel"
        const val JOB_ID = 1

        fun startService(context: Context, intent: Intent) {
            Util.startForegroundService(context, intent)
        }


        fun startDownload(
            context: Context,
            mediaUri: Uri,
        ) {
            val downloadRequest = DownloadRequest.Builder(mediaUri.toString(), mediaUri).build()
            sendAddDownload(
                context,
                MediaDownloadService::class.java,
                downloadRequest,
                true
            )
        }

    }
}