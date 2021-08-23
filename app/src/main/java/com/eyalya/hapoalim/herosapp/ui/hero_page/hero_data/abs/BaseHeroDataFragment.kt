package com.eyalya.hapoalim.herosapp.ui.hero_page.hero_data.abs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.eyalya.hapoalim.herosapp.common.ShareObject
import com.eyalya.hapoalim.herosapp.models.SharableObject

abstract class BaseHeroDataFragment<VB: ViewBinding, M: SharableObject> : Fragment() {

    abstract var binding: VB

    abstract var model : M

    abstract var shareButton: View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareButton.setOnClickListener {  ShareObject(model)  }
        initHeroData(model)
    }


    abstract fun initHeroData(model: M)
}