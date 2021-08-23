package com.eyalya.hapoalim.herosapp.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.eyalya.hapoalim.herosapp.common.loadImageFromCache
import com.eyalya.hapoalim.herosapp.models.BaseVH
import com.eyalya.hapoalim.herosapp.models.Hero


abstract class BasicHeroesAdapter<VH : BaseVH>(
    open val list: ArrayList<Hero> = arrayListOf(),
    open val listener: HeroClickCallback? = null
) : RecyclerView.Adapter<VH>() {


    interface HeroClickCallback {
        fun onHeroClick(hero: Hero, extras: FragmentNavigator.Extras)
        fun getNavController(): NavController
    }

    private fun getItem(position: Int): Hero? {
        return if (list.size > position) {
            list[position]
        } else null
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.apply {
            if (holder is TopHeroesAdapter.SmallHeroVH) {
                holder.bind(this, position + 1)
            } else if (holder is BasicHeroesAdapter<*>.BasicHeroVH) {
                holder.bind(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: ArrayList<Hero>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    abstract inner class BasicHeroVH(view: View) : BaseVH(view) {

        private var currentHero: Hero? = null

        abstract val heroName: TextView
        abstract val heroImage: ImageView

        init {
            view.setOnClickListener {
                currentHero?.let {
                    heroName.transitionName = it.name + "title"
                    heroImage.transitionName = it.name + "image"
                    val extras = FragmentNavigatorExtras(heroName to heroName.transitionName, heroImage to heroImage.transitionName)

                    listener?.onHeroClick(it, extras)
                }
            }
        }

        @CallSuper
        open fun bind(hero: Hero) {
            currentHero = hero
            heroName.text = hero.name
            heroImage.loadImageFromCache(hero.image)
        }
    }

}