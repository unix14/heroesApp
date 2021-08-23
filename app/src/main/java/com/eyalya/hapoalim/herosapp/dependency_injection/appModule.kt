package com.eyalya.hapoalim.herosapp.dependency_injection

import com.eyalya.hapoalim.herosapp.navigation.NavigationManager
import com.eyalya.hapoalim.herosapp.network.HeroApiService
import com.eyalya.hapoalim.herosapp.repos.HeroRepositoryImpl
import com.eyalya.hapoalim.herosapp.repos.Top3RepositoryImpl
import com.eyalya.hapoalim.herosapp.repos.abs.HeroRepository
import com.eyalya.hapoalim.herosapp.repos.abs.Top3Repository
import com.eyalya.hapoalim.herosapp.navigation.NavigationManagerImpl
import org.koin.dsl.module

val appModule = module {

    single { provideHeroRepository(get()) }

    single { provideTop3Repository(get()) }

    single { provideNavigationManager()}
}

fun provideNavigationManager() : NavigationManager = NavigationManagerImpl()

fun provideTop3Repository(heroRepository: HeroRepository): Top3Repository = Top3RepositoryImpl(heroRepository)

fun provideHeroRepository(apiService: HeroApiService): HeroRepository = HeroRepositoryImpl(apiService)