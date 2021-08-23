package com.eyalya.hapoalim.herosapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.AppearanceFragment
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.BiographyFragment
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.ConnectionsFragment
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.WorkFragment

class HeroDataAdapter(parent: Fragment, private val hero: Hero) : FragmentStateAdapter(parent) {

    companion object {
        const val TOTAL_ITEMS = 4// according to Hero params

        const val BIOGRAPHY = 0
        const val APPEARANCE = 1
        const val WORK = 2
        const val CONNECTIONS = 3
    }

    override fun getItemCount(): Int {
        return TOTAL_ITEMS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            BIOGRAPHY -> {
                BiographyFragment(hero.biography!!)
            }
            APPEARANCE -> {
                AppearanceFragment(hero.appearance!!)
            }
            WORK -> {
                WorkFragment(hero.work!!)
            }
            CONNECTIONS -> {
                ConnectionsFragment(hero.connections!!)
            }
            else -> {
                throw NotImplementedError("need to implement crateFragment in ${javaClass.simpleName} for position $position")
            }
        }
    }
}