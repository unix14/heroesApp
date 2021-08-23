package com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyalya.hapoalim.herosapp.databinding.FragmentPowerStatsBinding
import com.eyalya.hapoalim.herosapp.models.PowerStatsData
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.abs.BaseHeroDataFragment


class PowerStatsFragment(override var model: PowerStatsData) : BaseHeroDataFragment<FragmentPowerStatsBinding, PowerStatsData>() {

    override lateinit var binding : FragmentPowerStatsBinding

    override var shareButton: View
        get() = binding.shareBtn
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            binding = FragmentPowerStatsBinding.inflate(inflater,container, false)
        }
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun initHeroData(model: PowerStatsData) {
        binding.apply {
            intelligence.text = "Intelligence:  " + model.intelligence
            strength.text = "Strength:  " + model.strength
            speed.text = "Speed:  " + model.speed
            durability.text = "Durability:  " + model.durability
            combat.text = "Combat:  " + model.combat
        }
    }
}