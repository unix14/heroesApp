package com.eyalya.hapoalim.herosapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.eyalya.hapoalim.herosapp.databinding.TopSuperHeroItemBinding
import com.eyalya.hapoalim.herosapp.models.Hero

class TopHeroesAdapter(
    override val list: ArrayList<Hero> = arrayListOf(),
    override val listener: HeroClickCallback? = null
) : BasicHeroesAdapter<TopHeroesAdapter.SmallHeroVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallHeroVH {
        val binding = TopSuperHeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmallHeroVH(binding)
    }

    inner class SmallHeroVH(private val binding: TopSuperHeroItemBinding) :
        BasicHeroesAdapter<SmallHeroVH>.BasicHeroVH(binding.root) {

        override val heroName: TextView
            get() = binding.heroName
        override val heroImage: ImageView
            get() = binding.heroImage

        @SuppressLint("SetTextI18n")
        fun bind(hero: Hero, index: Int) {
            super.bind(hero)
            binding.index.text = "#$index"
        }
    }

}