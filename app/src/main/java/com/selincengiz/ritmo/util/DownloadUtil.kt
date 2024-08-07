package com.selincengiz.ritmo.util

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.offline.DefaultDownloadIndex
import androidx.media3.exoplayer.offline.DefaultDownloaderFactory
import androidx.media3.exoplayer.offline.DownloadManager
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.Executors

@OptIn(UnstableApi::class)
object DownloadUtil {
    private const val CACHE_SIZE: Long = 100 * 1024 * 1024 // 100 MB

    @Volatile
    private var downloadManager: DownloadManager? = null

    @Volatile
    private var downloadCache: SimpleCache? = null

    @Volatile
    var cacheDataSourceFactory: CacheDataSource.Factory? = null

    fun getDownloadManager(context: Context): DownloadManager {
        return downloadManager ?: synchronized(this) {
            downloadManager ?: buildDownloadManager(context).also { downloadManager = it }
        }
    }

    private fun buildDownloadManager(context: Context): DownloadManager {
        val databaseProvider: DatabaseProvider = StandaloneDatabaseProvider(context)
        val cache = getDownloadCache(context, databaseProvider)
        cacheDataSourceFactory = getDownloadCacheDataSource(cache)
        val downloadIndex = DefaultDownloadIndex(databaseProvider)
        val executor = Executors.newFixedThreadPool(4)
        val downloaderFactory = DefaultDownloaderFactory(cacheDataSourceFactory!!, executor)
        return DownloadManager(context, downloadIndex, downloaderFactory)
    }

    private fun getDownloadCacheDataSource(
        cache: SimpleCache
    ): CacheDataSource.Factory {
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(DefaultHttpDataSource.Factory())

        return cacheDataSourceFactory
    }

    private fun getDownloadCache(
        context: Context,
        databaseProvider: DatabaseProvider
    ): SimpleCache {
        return downloadCache ?: synchronized(this) {
            downloadCache ?: buildDownloadCache(context, databaseProvider).also {
                downloadCache = it
            }
        }
    }

    private fun buildDownloadCache(
        context: Context,
        databaseProvider: DatabaseProvider
    ): SimpleCache {
        val cacheDir = File(context.cacheDir, "media/downloads")
        return SimpleCache(cacheDir, LeastRecentlyUsedCacheEvictor(CACHE_SIZE), databaseProvider)
    }
}