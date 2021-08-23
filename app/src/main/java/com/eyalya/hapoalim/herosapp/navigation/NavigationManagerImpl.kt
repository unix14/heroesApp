package com.eyalya.hapoalim.herosapp.navigation

import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import com.eyalya.hapoalim.herosapp.models.Hero

class NavigationManagerImpl : NavigationManager() {

    override val navigationEventData = MutableLiveData<NavigationEvent>()

    override fun onShowHomePage() =
        navigationEventData.postValue(NavigationEvent.ShowHomePage())


    override fun onShowHero(hero: Hero, extras: FragmentNavigator.Extras?) =
        navigationEventData.postValue(NavigationEvent.ShowHeroPage(hero, extras))

}