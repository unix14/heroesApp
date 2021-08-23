package com.eyalya.hapoalim.herosapp.ui.hero_page

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.eyalya.hapoalim.herosapp.adapters.HeroDataAdapter
import com.eyalya.hapoalim.herosapp.ui.MainActivity
import com.eyalya.hapoalim.herosapp.adapters.HeroDataAdapter.Companion.TOTAL_ITEMS
import com.eyalya.hapoalim.herosapp.common.*
import com.eyalya.hapoalim.herosapp.data.Constants
import com.eyalya.hapoalim.herosapp.databinding.HeroPageFragmentBinding
import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.navigation.NavigationManager

class HeroPageFragment : Fragment() {

    //for auto scroll for view pager
    private var handler: Handler? = null
    private var slidingRunnable: Runnable? = null

    private lateinit var binding: HeroPageFragmentBinding

    private val navigationManager: NavigationManager
        get() = (activity as MainActivity).navigationManager

    private val hero: Hero?
        get() {
            return if (navigationManager.navigationEventData.value is NavigationManager.NavigationEvent.ShowHeroPage) {
                (navigationManager.navigationEventData.value as NavigationManager.NavigationEvent.ShowHeroPage).hero
            } else {
                null
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = HeroPageFragmentBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        enterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
        exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.no_transition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        slidingRunnable?.let { handler?.removeCallbacks(it) }
        slidingRunnable = null
        handler = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateUi()
        initUi()

        handler = Handler(Looper.getMainLooper())
        hero?.apply {
            bindData(this)
            initHeroViewPager()
        }
    }

    private fun initHeroViewPager() = with(binding.heroBioViewPager) {
        hero?.let {
            setPageTransformer(ZoomOutPageTransformer())
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = HeroDataAdapter(this@HeroPageFragment, it)
            startAutoSlider()
        }
    }

    private fun startAutoSlider() {
        slidingRunnable = Runnable {
            var pos: Int = binding.heroBioViewPager.currentItem
            pos++
            if (pos >= TOTAL_ITEMS) pos = 0
            binding.heroBioViewPager.currentItem = pos
            slidingRunnable?.let { handler?.postDelayed(it, 3000) }
        }
        handler?.postDelayed(slidingRunnable!!, 3000)
    }

    private fun animateUi() = with(binding) {
        heroBigImage.transitionName = hero?.name + "image"
        heroNameTitle.transitionName = hero?.name + "title"

        fullName.fadeInAnimate()
        placeOfBirth.fadeInAnimate()
        publisherText.fadeInAnimate()
    }

    private fun initUi() = with(binding) {
        heroShareButton.setOnClickListener { hero?.let { h -> ShareObject(h) } }
        publisherButton.setOnClickListener {
            val pub = hero?.biography?.publisher // query for google
            context?.openUrl(Constants.BASIC_GOOGLE_SEARCH_PREFIX + pub)
        }
    }

    private fun bindData(hero: Hero) = with(binding) {
        hero.let {
            heroNameTitle.text = it.name
            fullName.text = it.biography?.fullName
            heroBigImage.loadImageFromCache(it.image)

            placeOfBirth.text = it.biography?.placeOfBirth
            publisherText.text = it.biography?.publisher
        }
    }

}