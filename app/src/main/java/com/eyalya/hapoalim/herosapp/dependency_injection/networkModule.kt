package com.eyalya.hapoalim.herosapp.dependency_injection

import android.graphics.Bitmap
import com.eyalya.hapoalim.herosapp.cache.LruCacheManager
import com.eyalya.hapoalim.herosapp.cache.LruCacheManagerImpl
import com.eyalya.hapoalim.herosapp.data.Constants
import com.eyalya.hapoalim.herosapp.network.HeroApiService
import com.eyalya.hapoalim.herosapp.ui.home.HomeViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideHeroApiService(get()) }

    single { provideLruCacheManager()}


    viewModel { HomeViewModel(get(), get()) }
}

fun provideLruCacheManager() : LruCacheManager<Bitmap> = LruCacheManagerImpl()


fun provideHeroApiService(retrofit: Retrofit): HeroApiService = retrofit.create(HeroApiService::class.java)

fun provideDefaultOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder().addInterceptor(logging)
    return httpClient.build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss Z")
        .create()

    return Retrofit.Builder()
        .baseUrl(Constants.BASE_SERVER_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}