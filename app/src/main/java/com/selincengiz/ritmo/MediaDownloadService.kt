package com.selincengiz.ritmo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val notification = getForegroundNotification(emptyList(), 0)
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
    }

    override fun getDownloadManager(): DownloadManager {
        downloadManager = DownloadUtil.getDownloadManager(applicationContext)
        return downloadManager
    }

    override fun getScheduler(): Scheduler {
        return PlatformScheduler(applicationContext, JOB_ID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getForegroundNotification(
        downloads: List<Download>,
        notMetRequirements: Int
    ): Notification {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            DOWNLOAD_NOTIFICATION_CHANNEL_ID,
            getString(R.string.download_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.download_notification_channel_description)
        }
        notificationManager.createNotificationChannel(channel)

        val builder =
            NotificationCompat.Builder(applicationContext, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle(getString(R.string.download_notification_title))
                .setContentText(getString(R.string.download_notification_text))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)

        notificationManager.notify(1, builder.build())
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
            finished: () -> Unit
        ) {
            val downloadRequest = DownloadRequest.Builder(mediaUri.toString(), mediaUri).build()
            sendAddDownload(
                context,
                MediaDownloadService::class.java,
                downloadRequest,
                true
            )
            DownloadUtil.getDownloadManager(context).addListener(object : DownloadManager.Listener {
                override fun onDownloadChanged(
                    downloadManager: DownloadManager,
                    download: Download,
                    finalException: Exception?
                ) {
                    if (download.state == Download.STATE_COMPLETED) {
                        finished()
                    }
                }
            })
        }
    }
}