package com.eyalya.hapoalim.herosapp.cache

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import androidx.annotation.IntRange
import java.util.*
import java.util.concurrent.TimeUnit

abstract class LruCacheManager<T> {


    data class CacheItem<T>(var data: T) {
        var creationTime: Date = Date()
    }

    abstract var memoryCache: LruCache<String, CacheItem<T>>


    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    val maxMemory: Int
        get() = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    // Use 1/8th of the available memory for this memory cache.
    val cacheSize: Int
        get() = maxMemory / 8


    fun setCacheSize(@IntRange(from = 1) cacheSize: Int) {
        memoryCache.resize(cacheSize)
    }


    open fun getItemByIdFromCache(id: String): T? {
        Log.d("wow", "getItemByIdFromCache: $id") //replace all wow tags
        return memoryCache.get(id)?.data
    }

    open fun putItemByIdToCache(id: String, item: T): T? {
        Log.d("wow", "putItemByIdToCache: $id") //replace all wow tags
        return memoryCache.put(id, CacheItem(item))?.data
    }
}