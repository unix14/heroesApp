package com.eyalya.hapoalim.herosapp.cache

import android.graphics.Bitmap
import android.util.LruCache
import java.util.concurrent.TimeUnit

class LruCacheManagerImpl : LruCacheManager<Bitmap>() {

    private var expiredTimeInMinutes: Int = EXPIRED_TIME_IN_MIN

    companion object {
        private const val EXPIRED_TIME_IN_MIN = 1440 //24 Hours
    }

    override var memoryCache: LruCache<String, CacheItem<Bitmap>> =
        object : LruCache<String, CacheItem<Bitmap>>(cacheSize) {

            override fun sizeOf(key: String, bitmap: CacheItem<Bitmap>): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.data.byteCount / 1024
            }
        }

    fun setExpiredTime(expiredTimeInMinutes: Int) {
        this.expiredTimeInMinutes = expiredTimeInMinutes
    }

    fun clearAllExpired() {
        val snapshot = memoryCache.snapshot()
        for ((key, value) in snapshot) {
            removeIfExpired(key, memoryCache.get(key))
        }
    }

    override fun getItemByIdFromCache(id: String): Bitmap? {
        val cacheItem: CacheItem<Bitmap> = memoryCache[id] ?: return null

        return if (removeIfExpired(id, cacheItem)) {
            null
        } else {
            cacheItem.data
        }
    }

    /**
     * return true if item is Expired (and removed)
     */
    private fun removeIfExpired(key: String, cacheItem: CacheItem<Bitmap>): Boolean {
        val timeInterval = System.currentTimeMillis() - cacheItem.creationTime.time

        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInterval)

        if (minutes >= expiredTimeInMinutes) {
            memoryCache.remove(key)
            return true
        }
        return false
    }

}