package com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyalya.hapoalim.herosapp.databinding.FragmentConnectionsBinding
import com.eyalya.hapoalim.herosapp.models.HeroConnections
import com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.abs.BaseHeroDataFragment


class ConnectionsFragment(override var model: HeroConnections) : BaseHeroDataFragment<FragmentConnectionsBinding, HeroConnections>() {

    override lateinit var binding : FragmentConnectionsBinding

    override var shareButton: View
        get() = binding.shareBtn
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            binding = FragmentConnectionsBinding.inflate(inflater,container, false)
        }
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun initHeroData(model: HeroConnections) {
        binding.apply {
            groups.text = "Groups:  " + model.groups
            relatives.text = "Relatives:  " + model.relatives
        }
    }
}