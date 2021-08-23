package com.eyalya.hapoalim.herosapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.eyalya.hapoalim.herosapp.databinding.SuperHeroItemBinding
import com.eyalya.hapoalim.herosapp.models.Hero

class HeroesAdapter(
    override val list: ArrayList<Hero> = arrayListOf(),
    override val listener: HeroClickCallback? = null,
) : BasicHeroesAdapter<HeroesAdapter.HeroVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroVH {
        val binding =
            SuperHeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroVH(binding)
    }

    inner class HeroVH(private val binding: SuperHeroItemBinding) :
        BasicHeroesAdapter<HeroVH>.BasicHeroVH(binding.root) {

        override val heroName: TextView
            get() = binding.heroName
        override val heroImage: ImageView
            get() = binding.heroImage
    }
}