package com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyalya.hapoalim.herosapp.databinding.FragmentAppearanceBinding
import com.eyalya.hapoalim.herosapp.models.HeroAppearance
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.abs.BaseHeroDataFragment


class AppearanceFragment(override var model: HeroAppearance) : BaseHeroDataFragment<FragmentAppearanceBinding, HeroAppearance>() {

    override lateinit var binding : FragmentAppearanceBinding

    override var shareButton: View
        get() = binding.shareBtn
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            binding = FragmentAppearanceBinding.inflate(inflater,container, false)
        }
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun initHeroData(model: HeroAppearance) {
        binding.apply {
            gender.text = "Gender:  " + model.gender
            race.text = "Race:  " + model.race
            eyeColor.text = "Eye Color:  " + model.eyeColor
            hairColor.text = "Hair Color:  " + model.hairColor
        }
    }
}