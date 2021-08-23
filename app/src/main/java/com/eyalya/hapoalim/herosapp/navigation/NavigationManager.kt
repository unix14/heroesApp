package com.eyalya.hapoalim.herosapp.navigation

import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import com.eyalya.hapoalim.herosapp.models.Hero

abstract class NavigationManager {

    abstract val navigationEventData: MutableLiveData<NavigationEvent>

    sealed class NavigationEvent(open val extras: FragmentNavigator.Extras? = null) {
        class ShowHeroPage(val hero: Hero, extras: FragmentNavigator.Extras? = null) : NavigationEvent(extras)
        class ShowHomePage : NavigationEvent(null)
    }

    abstract fun onShowHomePage()
    abstract fun onShowHero(hero: Hero, extras: FragmentNavigator.Extras? = null)
}