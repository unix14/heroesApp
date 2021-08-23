package com.eyalya.hapoalim.herosapp

import android.app.Application
import com.eyalya.hapoalim.herosapp.dependency_injection.appModule
import com.eyalya.hapoalim.herosapp.dependency_injection.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class HeroApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@HeroApp)
            // declare modules
            modules(appModule)
            modules(networkModule)
        }
    }
}