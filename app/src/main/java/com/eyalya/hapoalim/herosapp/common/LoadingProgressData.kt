package com.eyalya.hapoalim.herosapp.common

import androidx.lifecycle.LiveData
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Implementation of Boolean LiveData class
 * that counts the number of startProgress and endProgress calls
 * and post value == true in case there is any unfinished progress.
 */
class LoadingProgressData : LiveData<Boolean>() {

    private var isLoading: AtomicBoolean  = AtomicBoolean(false)

    init {
        value = isLoading.get()
    }

    fun startProgress() {
        isLoading.set(true)
        postValue(isLoading.get())
    }

    fun endProgress() {
        isLoading.set(false)
        postValue(isLoading.get())
    }
}
