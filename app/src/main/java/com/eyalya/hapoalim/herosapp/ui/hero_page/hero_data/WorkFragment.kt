package com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyalya.hapoalim.herosapp.databinding.FragmentWorkBinding
import com.eyalya.hapoalim.herosapp.models.HeroWork
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.abs.BaseHeroDataFragment


class WorkFragment(override var model: HeroWork) : BaseHeroDataFragment<FragmentWorkBinding, HeroWork>() {

    override lateinit var binding : FragmentWorkBinding

    override var shareButton: View
        get() = binding.shareBtn
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            binding = FragmentWorkBinding.inflate(inflater,container, false)
        }
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun initHeroData(model: HeroWork) {
        binding.apply {
            occupation.text = "Occupation:  " + model.occupation
            base.text = "Base:  " + model.base
        }
    }
}