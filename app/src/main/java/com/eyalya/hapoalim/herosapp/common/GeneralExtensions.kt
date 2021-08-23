package com.eyalya.hapoalim.herosapp.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.load
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.eyalya.hapoalim.herosapp.ui.MainActivity
import com.eyalya.hapoalim.herosapp.R
import com.eyalya.hapoalim.herosapp.models.SharableObject
import com.eyalya.hapoalim.herosapp.models.SimpleResponse
import com.eyalya.hapoalim.herosapp.repos.abs.BasicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response


fun ImageView.loadImageFromCache(url: String) {
    val cache = MainActivity.bitmapCacheManager

    (cache.getItemByIdFromCache(url) as Bitmap?)?.apply {
        this@loadImageFromCache.loadBitmap(this)
        return
    }

    ImageLoader.Builder(context).okHttpClient {
        OkHttpClient.Builder()
            // Cache all image responses
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }.build().apply {
        this@loadImageFromCache.load(url, this) {
            crossfade(true)
//            placeholder(R.drawable.placeholder)
//            error(R.drawable.placeholder)
//            diskCachePolicy(CachePolicy.ENABLED)

            target { result ->
                val bitmap = (result as BitmapDrawable).bitmap

                cache.putItemByIdToCache(url, bitmap)
                this@loadImageFromCache.loadBitmap(bitmap)
            }
        }
    }
}


fun ImageView.loadImage(url: String) {
    ImageLoader.Builder(context).okHttpClient {
        OkHttpClient.Builder()
            // Cache all image responses
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }.build().apply {
        this@loadImage.load(url, this) {
            crossfade(true)
//            placeholder(R.drawable.placeholder)
//            error(R.drawable.placeholder)
            diskCachePolicy(CachePolicy.ENABLED)
        }
    }
}


fun ImageView.loadBitmap(bitmap: Bitmap) {
    ImageLoader.Builder(context).okHttpClient {
        OkHttpClient.Builder()
            // Cache all image responses
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }.build().apply {
        this@loadBitmap.load(bitmap, this) {
            crossfade(true)
//            placeholder(R.drawable.placeholder)
//            error(R.drawable.placeholder)
            diskCachePolicy(CachePolicy.ENABLED)
        }
    }
}
//i decided to remove placeholder and error drawables bcuz of unexpected flickering

fun View.fadeInAnimate(duration: Long = 1500) {
    clearAnimation()
    alpha = 0f
    visible()
    animate().setDuration(duration).alphaBy(1f).start()
}


fun Context.openUrl(url: String) {
    val builder = CustomTabsIntent.Builder()
    // modify toolbar color
    builder.setToolbarColor(ContextCompat.getColor(this, R.color.purple_700))
    // add share button to overflow men
    builder.addDefaultShareMenuItem()
    // show website title
    builder.setShowTitle(true)
    // animation for enter and exit of tab
    builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
    builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)

    builder.build().apply {
        val uri = Uri.parse(url)
        intent.setPackage(packageName)
        try {
            launchUrl(this@openUrl, uri)
        } catch (e: Exception) {
            startActivity(Intent(ACTION_VIEW, uri))
        }
    }
}


fun Fragment.ShareObject(hero: SharableObject) {
    Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, hero.shareText)
        startActivity(Intent.createChooser(this, "Share a Hero to"))
    }
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


fun Fragment.showKeyboard(view: View) {
    activity?.showKeyboard(view)
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun ViewModel.runCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch {
        block()
    }
}


inline fun <T> BasicRepository.safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
    return try {
        SimpleResponse.success(apiCall.invoke())
    } catch (e: Exception) {
        SimpleResponse.failure(e)
    }
}