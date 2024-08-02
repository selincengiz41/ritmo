package com.selincengiz.ritmo

import android.app.Notification
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.Scheduler
/*
@UnstableApi
class MyDownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    CHANNEL_ID,
    R.string.download_channel_name,
    R.string.download_channel_description
) {
    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000L
        private const val CHANNEL_ID = "download_channel"

        fun startDownload(context: Context, downloadRequest: DownloadRequest) {
            DownloadService.sendAddDownload(
                context,
                MyDownloadService::class.java,
                downloadRequest,
                /* foreground= */ false
            )
        }
    }

    override fun getDownloadManager(): DownloadManager {
        return applicationContext.downloadManager
    }

    override fun getScheduler(): Scheduler? {
        TODO("Not yet implemented")
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int
    ): Notification {
        return DownloadNotificationHelper(applicationContext, CHANNEL_ID).buildProgressNotification(
            this,
            R.drawable.exo_icon_play, // replace with your notification icon
            CHANNEL_ID,
            null,
            null,
            downloads,
            notMetRequirements
        )
    }

}*/