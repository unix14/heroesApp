package com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyalya.hapoalim.herosapp.databinding.FragmentBiographyBinding
import com.eyalya.hapoalim.herosapp.models.BiographyData
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.abs.BaseHeroDataFragment


class BiographyFragment(override var model: BiographyData) : BaseHeroDataFragment<FragmentBiographyBinding, BiographyData>() {

    override lateinit var binding : FragmentBiographyBinding

    override var shareButton: View
        get() = binding.shareBtn
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            binding = FragmentBiographyBinding.inflate(inflater,container, false)
        }
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun initHeroData(model: BiographyData) {
        binding.apply {
            alignment.text = "Alignment:  " + model.alignment
            alterEgos.text = "Alter Egos:  " + model.alterEgos
            firstAppearance.text = "First Appearance:  " + model.firstAppearance
            aliases.text = "Aliases:  " + model.getAliases
        }
    }
}